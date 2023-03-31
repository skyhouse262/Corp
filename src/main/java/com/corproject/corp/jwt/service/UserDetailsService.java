package com.corproject.corp.jwt.service;

import com.corproject.corp.jwt.model.vo.UserDetailsDto;
import com.corproject.corp.jwt.model.vo.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsService {
    @Autowired
    private UserService userService;

    public UserDetails loadUserByUsername(String userId) {
        UserDto userDto = UserDto.userBuilder().userId(userId).build();

        if(userId == null || userId.equals("")){
            return userService.login(userDto)
                    .map(u -> new UserDetailsDto(u, Collections.singleton(new SimpleGrantedAuthority(u.getUserId()))))
                    .orElseThrow(() -> new AuthenticationServiceException(userId));
        }else{
            return userService.login(userDto)
                    .map(u -> new UserDetailsDto(u, Collections.singleton(new SimpleGrantedAuthority(u.getUserId()))))
                    .orElseThrow(() -> new BadCredentialsException(userId));
        }
    }
}
