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

    POST_CREATED(HttpStatus.OK, "POST2005", "게시글 작성 성공"),
    POST_UPDATED(HttpStatus.OK, "POST2006", "게시글 수정 성공"),
    POST_DELETED(HttpStatus.OK, "POST2007", "게시글 삭제 성공"),
    POST_LIKE_SUCCESS(HttpStatus.OK, "POST2008", "게시글 좋아요 성공"),

    POST_COMMENT_CREATED(HttpStatus.OK, "COMMENT2004", "댓글 작성 성공"),
    POST_COMMENT_UPDATED(HttpStatus.OK, "COMMENT2005", "댓글 수정 성공"),
    POST_COMMENT_DELETED(HttpStatus.OK, "COMMENT2006", "댓글 삭제 성공"),
    POST_COMMENT_LIKE_SUCCESS(HttpStatus.OK, "COMMENT2007", "댓글 좋아요 성공"),

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
