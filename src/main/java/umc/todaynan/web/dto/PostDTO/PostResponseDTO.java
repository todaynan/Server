package umc.todaynan.web.dto.PostDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.todaynan.domain.enums.PostCategory;

import java.time.LocalDate;
import java.util.List;

public class PostResponseDTO {

    //글 작성, 삭제, 업데이트, 좋아요
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateResultDTO{ //글 작성
        private Long post_id;
        private Long user_id;
        private String title;
        private String content;
        private PostCategory category;

//        private String fieldTest = "test";
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateResultDTO{
        private Long post_id;
        private Long user_id;
        private String title;
        private String content;
        private PostCategory category;
//        private String fieldTest = "test";
    }

    //게시판 조회(페이징)
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostListDTO {
        List<PostDTO> postList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;

        public static class DeleteResultDTO {
            private String fieldTest = "test";
        }

        //게시글
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostDTO {
        Long postId;
        Long userId;
        String userNickname;
        String userAddress;
        String postTitle;
        String postContent;
        Integer postLike;
        Integer postComment;
        LocalDate createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeResultDTO {
        private Long post_like_id;
        private Long post_id;
        private Long user_id;
    }
}

