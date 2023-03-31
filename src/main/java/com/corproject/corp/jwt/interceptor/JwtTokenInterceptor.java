package com.corproject.corp.jwt.interceptor;

import com.corproject.corp.jwt.exception.BusinessExceptionHandler;
import com.corproject.corp.jwt.model.common.codes.ErrorCode;
import com.corproject.corp.jwt.util.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Log4j2
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException{
        String header = request.getHeader("Authorization");
        if(request.getMethod().equalsIgnoreCase("OPTIONS")){
            log.debug("if request options method is options, return true");
            return true;
        }
        if(header != null){
            String token = TokenUtils.getTokenFromHeader(header);

            if(TokenUtils.isValidToken(token)){
                String userId = TokenUtils.getUserIdFromToken(token);
                if(userId == null){
                    log.debug("token isn't userId");
                    throw new BusinessExceptionHandler("token isn't userId", ErrorCode.AUTH_TOKEN_NOT_MATCH);
                }
                return true;
            }else{
                throw new BusinessExceptionHandler("token is invalid", ErrorCode.AUTH_TOKEN_INVALID);
            }
        }else{
            throw new BusinessExceptionHandler("Header not exist token", ErrorCode.AUTH_TOKEN_IS_NULL);
        }
    }
}
