package com.corproject.corp.jwt.contoller;

import com.corproject.corp.jwt.dao.JwtRepository;
import com.corproject.corp.jwt.model.common.codes.AuthConstants;
import com.corproject.corp.jwt.model.common.codes.SuccessCode;
import com.corproject.corp.jwt.model.vo.User;
import com.corproject.corp.jwt.service.UserService;
import com.corproject.corp.jwt.util.TokenUtils;
import com.sun.net.httpserver.Authenticator;
import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/pre/api/")
public class JwtController {

    @Autowired
    UserService userService;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    JwtRepository jwtRepository;

    @RequestMapping("join")
    public JSONObject join(final HttpServletRequest request, final BCryptPasswordEncoder passwordEncoder){
        final JSONObject json = new JSONObject();
        final String id = request.getParameter("id");
        final String pwd = request.getParameter("password");
        final String name = request.getParameter("name");
        final String encodePwd = passwordEncoder.encode(pwd);
        final User user = new User();
        user.setId(id);
        user.setPassword(encodePwd);
        user.setName(name);
        int result = userService.userJoin(user);
        if(result == 1){
            json.put("code",1);
        }else{
            json.put("code",0);
        }
        return json;
    }

    @RequestMapping("login")
    public JSONObject login(final HttpServletRequest request, final BCryptPasswordEncoder passwordEncoder){
        final JSONObject json = new JSONObject();
        final String id = request.getParameter("id");
        final String password = request.getParameter("password");
        User umodel = new User();
        umodel.setId(id);
        umodel = userService.loginUser(umodel);
        if(umodel==null){
            json.put("code",3);
        }else{
            if(passwordEncoder.matches(password,umodel.getPassword())){
                final HashMap<String,Object> hmp = new HashMap<>();
                hmp.put("userSq",umodel.getUid());
                hmp.put("userId",umodel.getId());
                hmp.put("userPw",umodel.getPassword());
                hmp.put("userNm",umodel.getName());
                json.put("data",hmp);
                json.put("code",0);
            }else{
                json.put("code",1);
            }
        }
        return json;
    }

    @RequestMapping("/getAllUser")
    public JSONObject getAllUser(){
        final JSONObject json = new JSONObject();
        final JSONArray jarr = new JSONArray();

        final ArrayList<User> list = userService.getAllUser();
        for(User usr : list){
            final HashMap<String,Object> hmp = new HashMap<>();
            hmp.put("id",usr.getId());
            hmp.put("name",usr.getName());
            hmp.put("status",usr.getStatus());
            hmp.put("createDate",usr.getCreateDate());
            jarr.add(hmp);
        }
        json.put("data",jarr);
        return json;
    }

    @RequestMapping("/generateToken")
    public JSONObject makeToken(final HttpServletRequest request){
        final JSONObject json = new JSONObject();
        User user = new User();
        user.setUid(Integer.parseInt(request.getParameter("userSq")));
        user.setId(request.getParameter("userId"));
        user.setPassword(request.getParameter("userPw"));
        user.setName(request.getParameter("userNm"));

        String resultToken = TokenUtils.generateJwtTokenWithModel(user);

        json.put("result", AuthConstants.TOKEN_TYPE + " " + resultToken);
        json.put("resultCode", SuccessCode.SELECT.getStatus());
        json.put("resultMsg", SuccessCode.SELECT.getMessage());
        return json;
    }

}
