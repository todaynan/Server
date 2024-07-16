package umc.todaynan.converter;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.enums.LoginType;
import umc.todaynan.domain.enums.UserStatus;
import umc.todaynan.oauth2.Token;
import umc.todaynan.web.dto.UserDTO.UserDTO;
import umc.todaynan.web.dto.UserDTO.UserRequestDTO;
import umc.todaynan.web.dto.UserDTO.UserResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class UserConverter {
    public static UserDTO toUserDTO(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return UserDTO.builder()
                .email((String)attributes.get("email"))
                .nickname((String)attributes.get("name"))
                .build();
    }

    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickName())
                .build();
    }

    public UserResponseDTO.JoinResultDTO toJoinResultDTO(User user, Token token) {
        return UserResponseDTO.JoinResultDTO.builder()
                .user_id(user.getId())
                .created_at(user.getCreatedAt())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

    public static User toUser(UserRequestDTO.JoinUserDTO request, String email, LoginType loginType){

        return User.builder()
                .nickName(request.getNickName())
                .email(email)
                .loginType(loginType)
                .userPreferList(new ArrayList<>())
                .status(UserStatus.valueOf("ACTIVE"))
                .address(request.getAddress())
                .myPet(request.getMypet())
                .build();
    }

    public UserResponseDTO.AutoLoginResultDTO toAutoLoginResultDTO(User user, Token token, LocalDateTime date) {
        return UserResponseDTO.AutoLoginResultDTO.builder()
                .user_id(user.getId())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .expiration(date)
                .build();
    }

    public UserResponseDTO.LoginResultDTO toLoginResultDTO(User user, Token token, LocalDateTime date) {
        return UserResponseDTO.LoginResultDTO.builder()
                .user_id(user.getId())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .expiration(date)
                .build();
    }
}
