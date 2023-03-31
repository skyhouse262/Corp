package com.corproject.corp.jwt.model.common.codes;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode {
    //잘못된 서버 요청
    BAD_REQUEST_ERROR(400,"G001","Bad Request Exception"),
    //@RequestBody 데이터 부재
    REQUEST_BODY_MISSION_ERROR(400,"G002","Required request body is missing"),
    //유효하지 않은 타입
    INVALID_TYPE_VALUE(400,"G003","Invalid Type Value"),
    //Request Parameter로 데이터가 전달되지 않은 경우
    MISSION_REQUEST_PARAMETER_ERROR(400,"G004","Missing Servlet RequestParameter Exception"),
    //입/출력 값이 유효하지 않음
    IO_ERROR(400, "G008", "I/O Exception"),
    //JSON파싱 실패
    JSON_PARSE_ERROR(400,"G009","JsonParseException"),
    //com.fasterxml.jackson.core Processing Error
    JACKSON_PROCESS_ERROR(400, "G010", "com.fasterxml.jackson.core Exception"),
    //권한 없음
    FORBIDDEN_ERROR(403, "G004", "Forbidden Exception"),
    //서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(404,"G005","Not Found Exception"),
    //NULL Point Exception
    NOT_POINT_ERROR(404,"G006","Null Point Exception"),
    //@RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않은
    NOT_VALID_ERROR(404,"G007","handle Validation Exception"),
    //헤더에 데이터가 유효하지 않은 경우
    NOT_VALID_HEADER_ERROR(404,"G007","Header에 데이터가 존재하지 않는 경우"),
    //처리되지 않은 에러의 경우
    INTERNAL_SERVER_ERROR(500, "G999", "Internal Server Error Exception"),
    //인증 정보가 존재하지 않음
    AUTH_IS_NULL(200,"A404","AUTH_IS_NULL"),
    //토큰 조회 실패
    AUTH_TOKEN_FAIL(200,"A405","AUTH_TOKEN_FAIL"),
    //토큰이 유효하지 않음
    AUTH_TOKEN_INVALID(200,"A406","AUTH_TOKEN_INVALID"),
    //토큰이 일치하지 않음
    AUTH_TOKEN_NOT_MATCH(200,"A407","AUTH_TOKEN_NOT_MATH"),
    //토큰이 존재하지 않음
    AUTH_TOKEN_IS_NULL(200,"A408","AUTH_TOKEN_IS_NULL"),
    //?
    BUSINESS_EXCEPTION_ERROR(200,"8999","Business Exception Error"),
    INSERT_ERROR(200,"9999","Insert Transaction Error Exception"),
    UPDATE_ERROR(200,"9999","Update Transaction Error Exception"),
    DELETE_ERROR(200,"9999","Delete Transaction Error Exception");

    private int status;
    private String code;
    private String message;

    ErrorCode(final int status, final String code, final String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
