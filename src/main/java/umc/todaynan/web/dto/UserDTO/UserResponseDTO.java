package umc.todaynan.web.dto.UserDTO;

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
    public static class JoinResultDTO {
        private Long user_id;
        private LocalDateTime created_at;
        private String accessToken;
        private String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AutoLoginResultDTO {
        private Long user_id;
        private String accessToken;
        private String refreshToken;
        private LocalDateTime expiration;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO {
        private Long user_id;
        private String accessToken;
        private String refreshToken;
        private LocalDateTime expiration;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLikeResultDTO {
        private Long id;
        private String title;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetUserLikeListResultDTO {
        private List<UserLikeItems> userLikeItems;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLikeItems {
        private Long id;
        private String title;
        private PlaceCategory category;
        private String description;
        private String place_address;
        private String image;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;
    }
}
