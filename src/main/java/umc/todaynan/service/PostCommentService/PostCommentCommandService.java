package umc.todaynan.service.PostCommentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.exception.handler.PostCommentHandler;
import umc.todaynan.apiPayload.exception.handler.PostCommentLikeHandler;
import umc.todaynan.converter.PostCommentConverter;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.Post.PostComment.PostComment;
import umc.todaynan.domain.entity.Post.PostCommentLike.PostCommentLike;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.repository.PostCommentLikeRepository;
import umc.todaynan.repository.PostCommentRepository;
import umc.todaynan.service.PostService.PostCommandService;
import umc.todaynan.web.dto.PostCommentDTO.PostCommentRequestDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostCommentCommandService implements PostCommentCommandServiceImpl {
    @Autowired
    private final PostCommentRepository postCommentRepository;
    @Autowired
    private final PostCommandService postCommandService;
    @Autowired
    private final PostCommentLikeRepository postCommentLikeRepository;
    /*
    * 댓글 생성 API
    * 1. User 확인
    * 2. Request to DTO
    * 3. PostComment에 User, Post 세팅
    * 4. PostComment 저장
    * */
    @Override
    public PostComment createComment(Long post_id, PostCommentRequestDTO.CreatePostCommentDTO request, HttpServletRequest httpServletRequest) {
        User user = postCommandService.findUser(httpServletRequest);
        Post post = postCommandService.findPost(post_id, user);
        PostComment postComment = PostCommentConverter.toPostComment(request);
        postComment.setPost(post);
        postComment.setUser(user);
        return postCommentRepository.save(postComment);
//        return null;
    }

    /*
    * 댓글 수정 API
    * 1. User 확인
    * 2. 기존 PostComment에 새로운 데이터 저장
    * 3. PostComment 저장
    * */
    @Override
    public PostComment updateComment(Long post_id, Long comment_id, PostCommentRequestDTO.UpdatePostCommentDTO request, HttpServletRequest httpServletRequest) {
        User user = postCommandService.findUser(httpServletRequest);
        Post post = postCommandService.findPost(post_id, user);
        PostComment postComment = postCommentRepository.findById(comment_id)
                .orElseThrow(() -> new PostCommentHandler(ErrorStatus.POST_COMMENT_NOT_EXIST));
        postComment.setComment(request.getComment());
        return postCommentRepository.save(postComment);
    }

    /*
     * 댓글 삭제 API
     * 1. User 확인
     * 2. 해당 Post에서 User가 쓴 댓글 삭제
     * */
    @Override
    public Boolean deleteComment(Long post_id, Long comment_id, HttpServletRequest httpServletRequest) {
        User user = postCommandService.findUser(httpServletRequest);
        Post post = postCommandService.findPost(post_id, user);
        postCommentRepository.deleteById(comment_id);
        return true;
    }

    /*
    * 댓글 좋아요 API
    * 1. User 확인
    * 2. 해당 Post에 User가 좋아요 누른 댓글 확인
    * 3. 좋아요 누르지 않았다면 좋아요 누르기
    * */
    @Override
    public PostCommentLike likeComment(Long post_id, Long comment_id, HttpServletRequest httpServletRequest) {
        User user = postCommandService.findUser(httpServletRequest);
        Post post = postCommandService.findPost(post_id, user);
        Optional<PostCommentLike> byUserIdAndPostCommentId = postCommentLikeRepository.findByUserIdAndPostCommentId(user.getId(), comment_id);
        if(!byUserIdAndPostCommentId.isPresent()) {
            log.info("postcomment like not exist");
            PostComment postComment = postCommentRepository.findById(comment_id)
                    .orElseThrow(() -> new PostCommentHandler(ErrorStatus.POST_COMMENT_NOT_EXIST));
            PostCommentLike postCommentLike = toPostCommentLike(user, postComment);
            return postCommentLikeRepository.save(postCommentLike);
        }
        return null;
    }

    private PostCommentLike toPostCommentLike(User user, PostComment postComment) {
        return PostCommentLike.builder()
                .user(user)
                .postComment(postComment)
                .build();
    }
}
