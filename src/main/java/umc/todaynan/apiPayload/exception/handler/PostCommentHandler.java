package umc.todaynan.apiPayload.exception.handler;

import umc.todaynan.apiPayload.code.BaseErrorCode;
import umc.todaynan.apiPayload.exception.GeneralException;

public class PostCommentHandler extends GeneralException {
    public PostCommentHandler(BaseErrorCode code) {super(code);}
}
