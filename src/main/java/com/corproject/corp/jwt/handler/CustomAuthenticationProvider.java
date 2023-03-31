package com.corproject.corp.jwt.handler;

import com.corproject.corp.jwt.model.vo.UserDetailsDto;
import com.corproject.corp.jwt.service.UserDetailsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;
    @NonNull
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("2.CustomAuthenticationProvider");

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // 'AuthenticaionFilter' 에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String userId = token.getName();
        String userPw = (String) token.getCredentials();

        // Spring Security - UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        UserDetailsDto userDetailsDto = (UserDetailsDto) userDetailsService.loadUserByUsername(userId);

        if (!(userDetailsDto.getUserPw().equalsIgnoreCase(userPw) && userDetailsDto.getUserPw().equalsIgnoreCase(userPw))) {
            throw new BadCredentialsException(userDetailsDto.getUserNm() + "Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetailsDto, userPw, userDetailsDto.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
