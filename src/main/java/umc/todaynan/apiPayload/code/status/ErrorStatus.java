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
    USER_NOT_EXIST(HttpStatus.NOT_FOUND, "USER2004", "없는 계정입니다"),
    USER_ACCESS_TOKEN_NOT_VERITY(HttpStatus.INTERNAL_SERVER_ERROR, "USER2005", "잘못된 access 토큰 입니다."),

    POST_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "POST2001", "게시글 관련 오류"),
    POST_NOT_EXIST(HttpStatus.NOT_FOUND,"POST2002","없는 게시글입니다"),
    POST_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "POST2003", "게시글을 작성한 사용자를 찾을 수 없습니다."),
    POST_LIKE_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "POST2004", "이미 게시글 좋아요를 눌렀습니다"),

    POST_COMMENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMENT2001","댓글 관련 오류"),
    POST_COMMENT_NOT_EXIST(HttpStatus.NOT_FOUND,"COMMENT2002","없는 댓글입니다"),
    POST_COMMENT_LIKE_EXIST(HttpStatus.INTERNAL_SERVER_ERROR,"COMMENT2003","이미 댓글 좋아요를 눌렀습니다"),

    SEARCH_LOCATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SEARCH2001", "검색 결과 없음"),
    SEARCH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SEARCH2002", "검색 결과 없음"),

    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, " TOKEN2002", "토큰이 만료되었습니다. 재로그인 로직으로 돌아가야 합니다."),

    ChatRoom_NOT_FOUND(HttpStatus.BAD_REQUEST, "CHATROOM2001", "해당하는 채팅방을 찾을 수 없습니다.")

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
