package umc.todaynan.oauth2.converter;

import org.springframework.stereotype.Component;

public interface ProviderUserConverter <T, R> {
    R converter(T t);
}
