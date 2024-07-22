package umc.todaynan.converter;

import org.springframework.data.domain.Page;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.web.dto.PostDTO.PostResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static PostResponseDTO.PostDTO toPostDTO(Post post) {
        return PostResponseDTO.PostDTO.builder()
                .postId(post.getId())
                .userId(post.getUser().getId())
                .userNickname(post.getUser().getNickName())
                .userAddress(post.getUser().getAddress())
                .postTitle(post.getTitle())
                .postLike(post.getPostLikeList().size())
                .postComment(post.getPostCommentList().size())
                .createdAt(post.getCreatedAt().toLocalDate())
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
}
