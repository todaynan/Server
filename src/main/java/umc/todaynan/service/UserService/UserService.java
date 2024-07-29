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
    /**
     * Oauth2 관련 join Service
     * 사용 X, 유지만...
     */
    User join(String registrationId, ProviderUser providerUser);

    /**
     * User 회원가입, 로그인 관련 Service
     */
    User signupUser(UserRequestDTO.JoinUserRequestDTO joinUserDTO, String email, LoginType loginType);
    Boolean verifyNickName(String nickName);
    UserResponseDTO.AutoLoginResponseDTO autoLoginUser(HttpServletRequest httpServletRequest);
    UserResponseDTO.LoginResponseDTO loginUser(String email);

    /**
     * User 좋아요 관련 Service
     */
    Boolean deleteLikeItem(HttpServletRequest httpServletRequest, Long likeId);
    UserLike createLikeItem(HttpServletRequest httpServletRequest, UserRequestDTO.UserLikeRequestDTO userLikeDTO);
    UserResponseDTO.GetUserLikeListResponseDTO getLikeItems(HttpServletRequest httpServletRequest);

    /**
     * User 정보를 기반으로 User Prefer 목록 가져오는 Service
     */
    List<String> getPreferCategoryItems(HttpServletRequest httpServletRequest);

    void changeNickNameByUserId(long userId, UserRequestDTO.UserGeneralRequestDTO newNickname);
    void changeMyAddress(long userId, UserRequestDTO.UserGeneralRequestDTO newAddress);
    void userSignOut(long userId);
    long findUserIdByEmail(String email);
}
