package umc.todaynan.apiPayload.exception.handler;

import umc.todaynan.apiPayload.code.BaseErrorCode;
import umc.todaynan.apiPayload.exception.GeneralException;

public class PostCommentLikeHandler extends GeneralException {
    public PostCommentLikeHandler(BaseErrorCode code) {super(code);}
}
