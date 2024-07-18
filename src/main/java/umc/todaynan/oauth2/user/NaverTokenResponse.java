package umc.todaynan.oauth2.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverTokenResponse {
    private String id;
    private String nickname;
    private String email;
    private String name;
}