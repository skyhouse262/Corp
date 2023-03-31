package com.corproject.corp.jwt.model.common.codes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessCode {
    //조회 성공
    SELECT(200,"200","SELECT SUCCESS"),
    //삭제 성공
    DELETE(200,"200","DELETE SUCCESS"),
    //전송 성공
    SEND(200,"200","SEND SUCCESS"),
    //입력 성공
    INSERT(201,"201","INSERT SUCCESS"),
    //수정 성공
    UPDATE(204,"204","UPDATE SUCCESS");

    private int status;
    private String code;
    private String message;

    SuccessCode(final int status, final String code, final String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
