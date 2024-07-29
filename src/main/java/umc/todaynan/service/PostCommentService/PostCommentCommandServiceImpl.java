package umc.todaynan.service.PostCommentService;

import jakarta.servlet.http.HttpServletRequest;
import umc.todaynan.domain.entity.Post.PostComment.PostComment;
import umc.todaynan.domain.entity.Post.PostCommentLike.PostCommentLike;
import umc.todaynan.web.dto.PostCommentDTO.PostCommentRequestDTO;

public interface PostCommentCommandServiceImpl {
    public PostComment createComment(Long post_id, PostCommentRequestDTO.CreateDTO request, HttpServletRequest httpServletRequest);
    public PostComment updateComment(Long post_id, Long comment_id, PostCommentRequestDTO.UpdateDTO request, HttpServletRequest httpServletRequest);
    public Boolean deleteComment(Long post_id, Long comment_id, HttpServletRequest httpServletRequest);
    public PostCommentLike likeComment(Long post_id, Long comment_id, HttpServletRequest httpServletRequest);
}
