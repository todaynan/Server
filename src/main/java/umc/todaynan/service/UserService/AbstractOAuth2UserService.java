package umc.todaynan.service.UserService;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import umc.todaynan.oauth2.converter.ProviderUserConverter;
import umc.todaynan.oauth2.converter.ProviderUserRequest;
import umc.todaynan.oauth2.user.ProviderUser;
import umc.todaynan.repository.UserRepository;

@Service
@Getter
public abstract class AbstractOAuth2UserService {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    //회원 가입
    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest){
        //회원 있을 때 예외처리 필요
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        userService.join(clientRegistration.getRegistrationId(),providerUser);
    }

    // provider 유저객체 생성
    public ProviderUser providerUser(ProviderUserRequest providerUserRequest){

        return providerUserConverter.converter(providerUserRequest);
    }

}
