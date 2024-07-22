package umc.todaynan.web.dto.PostDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import umc.todaynan.domain.enums.PostCategory;

public class PostRequestDTO {
    @Getter
    public static class CreateDTO{
        @NotBlank
        String content;

        @NotBlank
        String title;

        @NotBlank
        PostCategory category;

        //location?
    }

    @Getter
    public static class UpdateDTO{
        @NotBlank
        String content;

        @NotBlank
        String title;
    }

    @Getter
    public static class DeleteDTO{
//        @NotNull
//        Long user_id;
    }

    @Getter
    public static class LikeDTO{
//        @NotNull
//        Long user_id;
    }
}
