package umc.todaynan.oauth2.converter;

import umc.todaynan.oauth2.config.OAuth2Config;
import umc.todaynan.oauth2.user.KakaoUser;
import umc.todaynan.oauth2.user.ProviderUser;
import umc.todaynan.oauth2.util.OAuth2Utils;

public class OAuth2KakaoProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser>{

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if(!providerUserRequest.clientRegistration().getRegistrationId().equals(OAuth2Config.SocialType.KAKAO.getSocialName())){
            return null;
        }
        return new KakaoUser(OAuth2Utils.getMainAttributes(
                providerUserRequest.oAuth2User()),
                providerUserRequest.oAuth2User(),
                providerUserRequest.clientRegistration()
        );
    }
}

