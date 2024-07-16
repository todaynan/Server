package umc.todaynan.web.dto.UserDTO;

import lombok.Getter;
import umc.todaynan.domain.enums.LoginType;
import umc.todaynan.domain.enums.MyPet;

import java.util.List;

public class UserRequestDTO {
    @Getter
    public static class JoinUserDTO {
        String nickName;
        List<Long> preferCategory;
        String address;
        MyPet mypet;
    }

    @Getter
    public static class LoginUserDTO {
        String email;
    }
}
