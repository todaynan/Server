package umc.todaynan.oauth2.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverTokenInfo {
    private String resultcode;
    private String message;
    private NaverTokenResponse response;
}
