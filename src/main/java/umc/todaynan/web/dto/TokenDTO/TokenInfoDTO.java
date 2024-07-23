package umc.todaynan.web.dto.TokenDTO;

import lombok.*;

public class TokenInfoDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleTokenInfoDTO {

        private String azp;
        private String aud;
        private String sub;
        private String scope;
        private String exp;
        private String expires_in;
        private String email;
        private String email_verified;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverTokenInfoDTO {
        private String resultcode;
        private String message;
        private NaverTokenResponseDTO response;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverTokenResponseDTO {
        private String id;
        private String nickname;
        private String email;
        private String name;
    }
}
