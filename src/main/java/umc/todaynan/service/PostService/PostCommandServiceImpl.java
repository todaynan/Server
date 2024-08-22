package umc.todaynan.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.Post.PostLike.PostLike;
import umc.todaynan.web.dto.PostDTO.PostRequestDTO;
import umc.todaynan.web.dto.PostDTO.PostResponseDTO;

public interface PostCommandServiceImpl {
    public Post createPost(PostRequestDTO.CreatePostDTO request, HttpServletRequest httpServletRequest);
    public Post updatePost(Long post_id, PostRequestDTO.UpdatePostDTO request, HttpServletRequest httpServletRequest);
    public Boolean deletePost(Long post_id, HttpServletRequest httpServletRequest);
    public PostLike likePost(Long post_id, HttpServletRequest httpServletRequest);
    public PostResponseDTO.PostDetailResultDTO getPostDetail(Long post_id, HttpServletRequest httpServletRequest);
}
