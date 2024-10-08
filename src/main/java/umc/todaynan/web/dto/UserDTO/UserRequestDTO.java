package umc.todaynan.web.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import umc.todaynan.domain.enums.LoginType;
import umc.todaynan.domain.enums.MyPet;
import umc.todaynan.domain.enums.PlaceCategory;

import java.util.List;

public class UserRequestDTO {
    @Getter
    public static class JoinUserRequestDTO {
        String nickName;
        List<Long> preferCategory;
        String address;
        MyPet mypet;
    }

    @Getter
    public static class LoginUserRequestDTO {
        String email;
    }

    @Getter
    public static class UserLikeRequestDTO {
        String title;
        String description;
        String placeAddress;
        String image;
        PlaceCategory category;
    }


    @Getter
    public static class UserGeneralRequestDTO {
        String request;
    }

    @Getter
    public static class UserInterestRequestDTO {
        List<Integer> interestList;
    }
}
