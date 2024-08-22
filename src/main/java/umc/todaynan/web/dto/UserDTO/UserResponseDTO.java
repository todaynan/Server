package umc.todaynan.web.dto.UserDTO;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.todaynan.domain.enums.PlaceCategory;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResponseDTO {
        private Long user_id;
        private LocalDateTime created_at;
        private String accessToken;
        private String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AutoLoginResponseDTO {
        private Long user_id;
        private String accessToken;
        private String refreshToken;
        private LocalDateTime expiration;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponseDTO {
        private Long user_id;
        private String accessToken;
        private String refreshToken;
        private LocalDateTime expiration;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLikeResponseDTO {
        private Long like_id;
        private String title;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetUserLikeListResponseDTO {
        private List<UserLikeItems> userLikeItems;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLikeItems {
        private Long like_id;
        private String title;
        private PlaceCategory category;
        private String description;
        private String place_address;
        private String image;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserModifyDTO {
        private String message;
    }


}
