package com.corproject.corp.jwt.handler;

import com.corproject.corp.jwt.model.vo.UserDetailsDto;
import com.corproject.corp.jwt.model.vo.UserDto;
import com.corproject.corp.jwt.util.ConvertUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserDto userDto = ((UserDetailsDto) authentication.getPrincipal()).getUserDto();
        JSONObject userObj = (JSONObject) ConvertUtil.convertObjectToJsonObject(userDto);

        HashMap<String,Object> responseMap = new HashMap<>();
        JSONObject jsonObject;
        if(userDto.getUserSt().equals("D")){
            responseMap.put("userInfo", userObj);
            responseMap.put("resultCode", 9001);
            responseMap.put("token", null);
            responseMap.put("failMsg", "휴면 계정입니다.");
            jsonObject = new JSONObject(responseMap);
        }else{
            responseMap.put("userInfo", userObj);
            responseMap.put("resultCode", 200);
            responseMap.put("failMsg",null);
            jsonObject = new JSONObject(responseMap);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jsonObject);
        printWriter.flush();
        printWriter.close();
    }
}
