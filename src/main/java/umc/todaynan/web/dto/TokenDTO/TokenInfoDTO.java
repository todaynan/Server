package umc.todaynan.web.dto.TokenDTO;

import lombok.*;

public class TokenInfoDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleTokenInfo {

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
    public static class NaverTokenInfo {
        private String resultcode;
        private String message;
        private NaverTokenResponse response;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverTokenResponse {
        private String id;
        private String nickname;
        private String email;
        private String name;
    }
}
