package umc.todaynan.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.entity.User.UserLike.UserLike;
import umc.todaynan.domain.enums.LoginType;
import umc.todaynan.oauth2.user.ProviderUser;
import umc.todaynan.web.dto.UserDTO.UserRequestDTO;
import umc.todaynan.web.dto.UserDTO.UserResponseDTO;

import java.util.List;

public interface UserService {
    public User join(String registrationId, ProviderUser providerUser);
    User joinUser(UserRequestDTO.JoinUserDTO joinUserDTO, String email, LoginType loginType);

    Boolean verifyNickName(String nickName);
    UserResponseDTO.AutoLoginResultDTO autoLoginUser(HttpServletRequest httpServletRequest);
    UserResponseDTO.LoginResultDTO loginUser(String email);

    UserLike likeLocation(HttpServletRequest httpServletRequest, UserRequestDTO.UserLikeDTO userLikeDTO);
    UserResponseDTO.GetUserLikeListResultDTO likeLocationList(HttpServletRequest httpServletRequest);

    List<String> getPreferCategoryList(HttpServletRequest httpServletRequest);

    void changeNickNameByUserId(long userId, String newNickname);
    void changeMyAddress(long userId, String newAddress);
    void userSignOut(long userId);
    long findUserIdByEmail(String email);
}