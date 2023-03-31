package com.corproject.corp.jwt.model.common.codes;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseCode {
    private String code;
    private String message;
    private int status;
    @Builder
    ResponseCode(String code, String message, int status){
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
