package umc.todaynan.converter;

import umc.todaynan.domain.entity.Post.PostComment.PostComment;
import umc.todaynan.domain.entity.Post.PostCommentLike.PostCommentLike;
import umc.todaynan.web.dto.PostCommentDTO.PostCommentRequestDTO;
import umc.todaynan.web.dto.PostCommentDTO.PostCommentResponseDTO;

public class PostCommentConverter {
    public static PostComment toPostComment(PostCommentRequestDTO.CreateDTO request) {
        return PostComment.builder()
                .comment(request.getComment())
                .build();
    }

    public static PostCommentResponseDTO.CreateResultDTO toCreateResultDTO(PostComment postComment) {
        return PostCommentResponseDTO.CreateResultDTO.builder()
                .post_comment_id(postComment.getId())
                .post_id(postComment.getPost().getId())
                .user_id(postComment.getUser().getId())
                .comment(postComment.getComment())
                .build();
//        return null;
    }

    public static PostCommentResponseDTO.UpdateResultDTO toUpdateResultDTO(PostComment postComment) {
        return PostCommentResponseDTO.UpdateResultDTO.builder()
                .post_comment_id(postComment.getId())
                .post_id(postComment.getPost().getId())
                .user_id(postComment.getUser().getId())
                .comment(postComment.getComment())
                .build();
        //        return null;
    }

    public static PostCommentResponseDTO.LikeResultDTO toLikeResultDTO(PostCommentLike  postCommentLike) {
        return PostCommentResponseDTO.LikeResultDTO.builder()
                .post_comment_like_id(postCommentLike.getId())
                .post_comment_id(postCommentLike.getPostComment().getId())
                .user_id(postCommentLike.getUser().getId())
                .build();
        //        return null;
    }
}
