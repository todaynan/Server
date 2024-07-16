package umc.todaynan.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.enums.LoginType;
import umc.todaynan.oauth2.user.ProviderUser;
import umc.todaynan.web.dto.UserDTO.UserRequestDTO;
import umc.todaynan.web.dto.UserDTO.UserResponseDTO;

public interface UserService {
    public User join(String registrationId, ProviderUser providerUser);
    User joinUser(UserRequestDTO.JoinUserDTO joinUserDTO, String email, LoginType loginType);

    Boolean verifyNickName(String nickName);
    UserResponseDTO.AutoLoginResultDTO autoLoginMember(HttpServletRequest httpServletRequest);
    UserResponseDTO.LoginResultDTO loginUser(String email);
}
