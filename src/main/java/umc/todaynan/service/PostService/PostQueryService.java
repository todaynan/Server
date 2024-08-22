package umc.todaynan.service.PostService;

import org.springframework.data.domain.Page;
import umc.todaynan.domain.entity.Post.Post.Post;

public interface PostQueryService {

    Page<Post> getEmployPostList(Integer page, String middleAddress);
    Page<Post> getChatPostList(Integer page, String middleAddress);
    Page<Post> getSuggestPostList(Integer page, String middleAddress);
    Page<Post> getHotPostList(Integer page);
    Page<Post> getUserPostListByUserId(long userId, Integer page);
}
