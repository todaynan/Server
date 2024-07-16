package umc.todaynan.web.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
