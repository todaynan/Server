package umc.todaynan.oauth2.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleTokenInfo {

    private String azp;
    private String aud;
    private String sub;
    private String scope;
    private String exp;
    private String expires_in;
    private String email;
    private String email_verified;
}

