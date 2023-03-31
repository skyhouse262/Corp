package com.corproject.corp.jwt.filter;

import com.corproject.corp.jwt.exception.BusinessExceptionHandler;
import com.corproject.corp.jwt.model.common.codes.AuthConstants;
import com.corproject.corp.jwt.model.common.codes.ErrorCode;
import com.corproject.corp.jwt.util.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.json.simple.JSONObject;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws IOException, ServletException {
        List<String> list = Arrays.asList(
                "/pre/api/*"
        );

        for(String str : list){
            if(str.contains("*")){
                str = str.split("\\*")[0];
                if(request.getRequestURI().contains(str)){
                    chain.doFilter(request,response);
                    return;
                }
            }
        }

        if(list.contains(request.getRequestURI())){
            chain.doFilter(request,response);
            return;
        }

        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(AuthConstants.AUTH_HEADER);
        logger.debug("[+] header Check: " + header);

        try {
            if (header != null && !header.equalsIgnoreCase("")) {
                String token = TokenUtils.getTokenFromHeader(header);
                if (TokenUtils.isValidToken(token)) {
                    String userId = TokenUtils.getUserIdFromToken(token);
                    logger.debug("[+] userId Check: " + userId);
                    if (userId != null && !userId.equalsIgnoreCase("")) {
                        chain.doFilter(request, response);
                    } else {
                        throw new BusinessExceptionHandler("TOKEN isn't userId", ErrorCode.BUSINESS_EXCEPTION_ERROR);
                    }
                } else {
                    throw new BusinessExceptionHandler("TOKEN is invalid", ErrorCode.BUSINESS_EXCEPTION_ERROR);
                }
            }
            else {
                throw new BusinessExceptionHandler("Token is null", ErrorCode.BUSINESS_EXCEPTION_ERROR);
            }
        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObject = jsonResponseWrapper(e);
            printWriter.print(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }

    private JSONObject jsonResponseWrapper(Exception e) {

        String resultMsg = "";
        if (e instanceof ExpiredJwtException) {
            resultMsg = "TOKEN Expired";
        }
        else if (e instanceof SignatureException) {
            resultMsg = "TOKEN SignatureException Login";
        }
        else if (e instanceof JwtException) {
            resultMsg = "TOKEN Parsing JwtException";
        }
        else {
            resultMsg = "OTHER TOKEN ERROR";
        }
        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", 401);
        jsonMap.put("code", "9999");
        jsonMap.put("message", resultMsg);
        jsonMap.put("reason", e.getMessage());
        JSONObject jsonObject = new JSONObject(jsonMap);
        logger.error(resultMsg, e);
        return jsonObject;
    }
}
