package umc.todaynan.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import umc.todaynan.apiPayload.code.BaseCode;
import umc.todaynan.apiPayload.code.ReasonDTO;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    _OK(HttpStatus.OK, "OK200", "성공"),

    USER_JOIN(HttpStatus.OK, "USER2005", "회원가입 성공"),
    USER_NICKNAME_VERIFY(HttpStatus.OK, "USER2006", "중복 검사 통과"),
    USER_LOGIN(HttpStatus.OK, "USER2007", "로그인 성공"),

    TOKEN_REFRESHED(HttpStatus.OK,"TOKEN2001", "토큰이 갱신되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
