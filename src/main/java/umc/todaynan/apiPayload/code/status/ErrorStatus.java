package umc.todaynan.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.todaynan.apiPayload.code.BaseErrorCode;
import umc.todaynan.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    PREFER_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "PREFER2001", "선호 카테고리가 없습니다."),


    USER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "USER2001", "사용자 관련 오류"),
    USER_NICKNAME_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "USER2002", "닉네임 중복"),
    USER_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "USER2003", "이미 있는 계정입니다"),
    USER_ACCESS_TOKEN_NOT_VERITY(HttpStatus.INTERNAL_SERVER_ERROR, "USER2004", "잘못된 access 토큰 입니다.");
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
