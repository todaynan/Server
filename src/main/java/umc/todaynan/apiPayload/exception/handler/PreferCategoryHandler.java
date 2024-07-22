package umc.todaynan.apiPayload.exception.handler;

import umc.todaynan.apiPayload.code.BaseErrorCode;
import umc.todaynan.apiPayload.exception.GeneralException;

public class PreferCategoryHandler extends GeneralException {
    public PreferCategoryHandler(BaseErrorCode code) {
        super(code);
    }
}
