package umc.todaynan.apiPayload.exception.handler;

import umc.todaynan.apiPayload.code.BaseErrorCode;
import umc.todaynan.apiPayload.exception.GeneralException;

public class PostLikeHandler extends GeneralException {
    public PostLikeHandler(BaseErrorCode code) {super(code);}
}
