package umc.todaynan.converter;

import umc.todaynan.domain.entity.Post.PostComment.PostComment;
import umc.todaynan.domain.entity.Post.PostCommentLike.PostCommentLike;
import umc.todaynan.web.dto.PostDTO.PostRequestDTO;
import umc.todaynan.web.dto.PostDTO.PostResponseDTO;

public class PostCommentConverter {
    public static PostComment toPostComment(PostRequestDTO.CreatePostCommentDTO request) {
        return PostComment.builder()
                .comment(request.getComment())
                .build();
    }

    public static PostResponseDTO.CreatePostCommentResultDTO toCreateResultDTO(PostComment postComment) {
        return PostResponseDTO.CreatePostCommentResultDTO.builder()
                .post_comment_id(postComment.getId())
                .post_id(postComment.getPost().getId())
                .user_id(postComment.getUser().getId())
                .comment(postComment.getComment())
                .build();
//        return null;
    }

    public static PostResponseDTO.UpdatePostCommentResultDTO toUpdateResultDTO(PostComment postComment) {
        return PostResponseDTO.UpdatePostCommentResultDTO.builder()
                .post_comment_id(postComment.getId())
                .post_id(postComment.getPost().getId())
                .user_id(postComment.getUser().getId())
                .comment(postComment.getComment())
                .build();
        //        return null;
    }

    public static PostResponseDTO.LikePostCommentResultDTO toLikeResultDTO(PostCommentLike  postCommentLike) {
        return PostResponseDTO.LikePostCommentResultDTO.builder()
                .post_comment_like_id(postCommentLike.getId())
                .post_comment_id(postCommentLike.getPostComment().getId())
                .user_id(postCommentLike.getUser().getId())
                .build();
        //        return null;
    }
}
