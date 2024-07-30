package umc.todaynan.converter;

import org.springframework.data.domain.Page;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.Post.PostComment.PostComment;
import umc.todaynan.domain.entity.Post.PostLike.PostLike;
import umc.todaynan.web.dto.PostDTO.PostRequestDTO;
import umc.todaynan.web.dto.PostDTO.PostResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post toPost(PostRequestDTO.CreatePostDTO request) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                //서비스에서 user_id 검증한 후, post 엔티티에 set user해야 함
                .build();
    }

    public static PostResponseDTO.CreatePostResultDTO toCreateResultDTO(Post post) {
        return PostResponseDTO.CreatePostResultDTO.builder()
                .post_id(post.getId())
                .user_id(post.getUser().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .build();
    }

    public static PostResponseDTO.PostDTO toPostDTO(Post post) {
        return PostResponseDTO.PostDTO.builder()
                .postId(post.getId())
                .userId(post.getUser().getId())
                .userNickname(post.getUser().getNickName())
                .userAddress(post.getUser().getAddress())
                .postTitle(post.getTitle())
                .postLike(post.getPostLikeList().size())
                .postComment(post.getPostCommentList().size())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static PostResponseDTO.PostListDTO toPostListDTO(Page<Post> postList) {
        List<PostResponseDTO.PostDTO> postDTOList = postList.stream()
                .map(PostConverter::toPostDTO).collect(Collectors.toList());

        return PostResponseDTO.PostListDTO.builder()
                .isLast(postList.isLast())
                .isFirst(postList.isFirst())
                .totalPage(postList.getTotalPages())
                .totalElements(postList.getTotalElements())
                .listSize(postDTOList.size())
                .postList(postDTOList)
                .build();
    }

    public static PostResponseDTO.UpdatePostResultDTO toUpdateResultDTO(Post post) {
        return PostResponseDTO.UpdatePostResultDTO.builder()
                .post_id(post.getId())
                .user_id(post.getUser().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .build();
    }


    public static PostResponseDTO.LikePostResultDTO toLikeResultDTO(PostLike postLike) {
        return PostResponseDTO.LikePostResultDTO.builder()
                .post_like_id(postLike.getId())
                .post_id(postLike.getPost().getId())
                .user_id(postLike.getUser().getId())
                .build();
    }

}


