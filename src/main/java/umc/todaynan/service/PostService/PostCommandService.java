package umc.todaynan.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.exception.handler.PostHandler;
import umc.todaynan.apiPayload.exception.handler.PostLikeHandler;
import umc.todaynan.apiPayload.exception.handler.UserHandler;
import umc.todaynan.converter.PostConverter;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.Post.PostLike.PostLike;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.repository.PostLikeRepository;
import umc.todaynan.repository.PostRepository;
import umc.todaynan.repository.UserRepository;
import umc.todaynan.web.dto.PostDTO.PostRequestDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostCommandService implements PostCommandServiceImpl{
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PostLikeRepository postLikeRepository;
    @Autowired
    private final TokenService tokenService;

    /*
     * 게시글 생성 API
     * 1. User 확인
     * 2. Request to DTO
     * 3. Post에 User 세팅
     * 4. Post 저장
     * */
    @Transactional
    @Override
    public Post createPost(PostRequestDTO.CreatePostDTO request, HttpServletRequest httpServletRequest) {
        User user = findUser(httpServletRequest);
        Post post = PostConverter.toPost(request);
        post.setUser(user);
        return postRepository.save(post);
    }

    /*
     * 게시글 수정 API
     * 1. User 확인
     * 2. 기존 Post에 새로운 데이터 저장
     * 3. Post 저장
     * */
    @Override
    public Post updatePost(Long post_id, PostRequestDTO.UpdatePostDTO request, HttpServletRequest httpServletRequest){
        User user = findUser(httpServletRequest);
        Post post = findPost(post_id, user);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        return postRepository.save(post);
    }

    /*
    * 게시글 삭제 API
    * 1. User 확인
    * 2. User가 쓴 Post 삭제
    * */
    @Override
    public Boolean deletePost(Long post_id, HttpServletRequest httpServletRequest){
        User user = findUser(httpServletRequest);
        Post post = findPost(post_id, user);
        postRepository.deleteById(post_id); // post 삭제
        return true;
    }

    /*
    * 게시글 좋아요 API
    * 1. User 확인
    * 2. User가 쓴 Post 확인
    * 3. PostLike에 user_id, post_id 저장
    * */
    @Override
    public PostLike likePost(Long post_id, HttpServletRequest httpServletRequest){
        User user = findUser(httpServletRequest);
        Post post = findPost(post_id, user);
        Optional<PostLike> byUserAndPost = postLikeRepository.findByUserAndPost(user, post);
        if(!byUserAndPost.isPresent()){
            log.info("post like not exist");
            PostLike postLike = toPostLike(user, post);
            return postLikeRepository.save(postLike);
        }
        return null;
    }

    public User findUser(HttpServletRequest httpServletRequest){
        String email = tokenService.getUid(tokenService.getJwtFromHeader(httpServletRequest));
        User user = userRepository.findByEmail(email) //헤더 정보에서 추출한 이메일로 체크
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_EXIST));
        return user;
    }

    public Post findPost(Long post_id, User user){
        Post post = postRepository.findByIdAndUserId(post_id, user.getId())
                .orElseThrow(() -> new PostHandler(ErrorStatus.POST_NOT_EXIST));
        return post;
    }

    private PostLike toPostLike(User user, Post post) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .build();
    }
//    private PostLike toPostLike(User user, Post post) {
//        return new PostLike(
//                post.getId(),
//                post.getTitle(),
//                post.getContent(),
////                post.getAuthor().getName()
//        );
//    }

}
