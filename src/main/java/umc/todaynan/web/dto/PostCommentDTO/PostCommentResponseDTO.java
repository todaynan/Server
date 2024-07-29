package umc.todaynan.web.dto.PostCommentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostCommentResponseDTO {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResultDTO{
        private Long post_comment_id;
        private Long post_id;
        private Long user_id;
        private String comment;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateResultDTO{
        private Long post_comment_id;
        private Long post_id;
        private Long user_id;
        private String comment;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LikeResultDTO{
        private Long post_comment_like_id;
        private Long post_comment_id;
        private Long user_id;
    }
}
