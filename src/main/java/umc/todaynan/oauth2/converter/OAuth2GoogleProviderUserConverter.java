package umc.todaynan.oauth2.converter;

import umc.todaynan.oauth2.config.OAuth2Config;
import umc.todaynan.oauth2.user.GoogleUser;
import umc.todaynan.oauth2.user.ProviderUser;
import umc.todaynan.oauth2.util.OAuth2Utils;

public class OAuth2GoogleProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser>{
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if(!providerUserRequest.clientRegistration().getRegistrationId().equals(OAuth2Config.SocialType.GOOGLE.getSocialName())){
            return null;
        }
        return new GoogleUser(OAuth2Utils.getMainAttributes(providerUserRequest.oAuth2User()),
                providerUserRequest.oAuth2User(),
                providerUserRequest.clientRegistration()
        );
    }
}