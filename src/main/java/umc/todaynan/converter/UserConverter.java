package umc.todaynan.converter;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.entity.User.UserLike.UserLike;
import umc.todaynan.domain.entity.User.UserPrefer.UserPrefer;
import umc.todaynan.domain.enums.LoginType;
import umc.todaynan.domain.enums.UserStatus;
import umc.todaynan.oauth2.Token;
import umc.todaynan.web.dto.UserDTO.UserDTO;
import umc.todaynan.web.dto.UserDTO.UserRequestDTO;
import umc.todaynan.web.dto.UserDTO.UserResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public UserLike toUserLike(User user, UserRequestDTO.UserLikeDTO userLikeDTO) {
        return UserLike.builder()
                .title(userLikeDTO.getTitle())
                .user(user)
                .category(userLikeDTO.getCategory())
                .description(userLikeDTO.getDescription())
                .placeAddress(userLikeDTO.getPlaceAddress())
                .image(userLikeDTO.getImage())
                .build();
    }

    public UserResponseDTO.UserLikeResultDTO toUserLikeResultDTO(UserLike userLike) {
        return UserResponseDTO.UserLikeResultDTO.builder()
                .id(userLike.getId())
                .title(userLike.getTitle())
                .created_at(userLike.getCreatedAt())
                .updated_at(userLike.getUpdatedAt())
                .build();
    }

    public UserResponseDTO.GetUserLikeListResultDTO toUserLikeListResultDTO(List<UserLike> userLikeList) {
        List<UserResponseDTO.UserLikeItems> userLikeItemsList = userLikeList.stream()
                .map(userLike ->
                        toUserLikeListResultFromUserLike(userLike)
                ).collect(Collectors.toList());
        return UserResponseDTO.GetUserLikeListResultDTO.builder()
                                .userLikeItems(userLikeItemsList)
                                .build();
    }

    public UserResponseDTO.UserLikeItems toUserLikeListResultFromUserLike(UserLike userLike) {
        return UserResponseDTO.UserLikeItems.builder()
                .id(userLike.getId())
                .title(userLike.getTitle())
                .description(userLike.getDescription())
                .image(userLike.getImage())
                .place_address(userLike.getPlaceAddress())
                .category(userLike.getCategory())
                .created_at(userLike.getCreatedAt())
                .updated_at(userLike.getUpdatedAt())
                .build();
    }
}
