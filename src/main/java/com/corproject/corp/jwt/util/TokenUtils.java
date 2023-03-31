package com.corproject.corp.jwt.util;

import com.corproject.corp.jwt.model.vo.User;
import com.corproject.corp.jwt.model.vo.UserDto;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class TokenUtils {

    private static final String jwtSecretKey = "exampleSecretKey";

    public static String generateJwtToken(UserDto userDto) {
        // 사용자 시퀀스를 기준으로 JWT 토큰을 발급하여 반환해줍니다.
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())                              // Header 구성
                .setClaims(createClaims(userDto))                       // Payload - Claims 구성
                .setSubject(String.valueOf(userDto.getUserSq()))        // Payload - Subject 구성
                .signWith(SignatureAlgorithm.HS256, createSignature())  // Signature 구성
                .setExpiration(createExpiredDate());                    // Expired Date 구성
        return builder.compact();
    }

    public static String generateJwtTokenWithModel(User user){
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaimsWithModel(user))
                .setSubject(String.valueOf(user.getUid()))
                .signWith(SignatureAlgorithm.HS256, createSignature())
                .setExpiration(createExpiredDate());
        return builder.compact();
    }

    public static String parseTokenToUserInfo(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean isValidToken(String token){
        try{
            Claims claims = getClaimsFormToken(token);
            log.info("expireTime : " + claims.getExpiration());
            log.info("userId : " + claims.get("userId"));
            log.info("userNm : " + claims.get("userNm"));

            return true;
        }catch (ExpiredJwtException exception){
            log.error("Token expired");
            return false;
        }catch (JwtException exception){
            log.error("Token tampered");
            return false;
        }catch (NullPointerException exception){
            log.error("Token is null");
            return false;
        }
    }

    public static String getTokenFromHeader(String header) { return header.split(" ")[1]; }

    private static Date createExpiredDate(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 8);
        return c.getTime();
    }

    private static Map<String,Object> createHeader(){
        Map<String,Object> header = new HashMap<>();
        header.put("type","JWT");
        header.put("alg","HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Map<String,Object> createClaims(UserDto userDto){
        Map<String, Object> claims = new HashMap<>();
        log.info("userId : ", userDto.getUserId());
        log.info("userNm : ", userDto.getUserNm());
        claims.put("userId", userDto.getUserId());
        claims.put("userNm", userDto.getUserNm());
        return claims;
    }

    private static Map<String,Object> createClaimsWithModel(User user){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.getId());
        claims.put("userNm",user.getName());
        claims.put("userSq",user.getUid());
        return claims;
    }

    private static Key createSignature(){
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Claims getClaimsFormToken(String token){
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .parseClaimsJws(token).getBody();
    }

    public static String getUserIdFromToken(String token){
        Claims claims = getClaimsFormToken(token);
        return claims.get("userId").toString();
    }
}
