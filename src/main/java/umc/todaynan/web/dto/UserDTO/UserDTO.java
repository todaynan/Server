package umc.todaynan.web.dto.UserDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDTO {
    private String nickname;
    private String email;

    @Builder
    public UserDTO(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }


}