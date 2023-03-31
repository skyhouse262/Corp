package com.corproject.corp.jwt.model.vo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    // 사용자 시퀀스
    private int userSq;

    // 사용자 아이디
    private String userId;

    // 사용자 패스워드
    private String userPw;

    // 사용자 이름
    private String userNm;

    // 사용자 상태
    private String userSt = "S";

    private Date createDate;


    @Builder(builderMethodName = "userBuilder", toBuilder = true)
    private UserDto(int userSq, String userId, String userPw, String userNm, String userSt
    ) {
        this.userSq = userSq;
        this.userId = userId;
        this.userPw = userPw;
        this.userNm = userNm;
        this.userSt = userSt;
    }
}
