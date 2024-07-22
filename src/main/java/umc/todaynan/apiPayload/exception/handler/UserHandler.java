package umc.todaynan.apiPayload.exception.handler;

import umc.todaynan.apiPayload.code.BaseErrorCode;
import umc.todaynan.apiPayload.exception.GeneralException;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode code) {
        super(code);
    }
}
