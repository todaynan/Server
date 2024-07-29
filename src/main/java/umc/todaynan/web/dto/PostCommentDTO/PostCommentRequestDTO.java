package umc.todaynan.web.dto.PostCommentDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class PostCommentRequestDTO {
    @Getter
    public static class CreatePostCommentDTO{
        @NotBlank
        String comment;
    }

    @Getter
    public static class UpdatePostCommentDTO{
        @NotBlank
        String comment;
    }

    @Getter
    public static class DeleteDTO{
    }

    @Getter
    public static class LikeDTO{
    }
}
