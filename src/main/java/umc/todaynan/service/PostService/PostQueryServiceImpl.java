package umc.todaynan.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.enums.PostCategory;
import umc.todaynan.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryServiceImpl implements PostQueryService{

    private final PostRepository postRepository;


    @Override
    public Page<Post> getEmployPostList(Integer page, String middleAddress){

        return postRepository
                .findAllByCategoryAndUser_AddressContainingOrderByCreatedAtDesc(PostCategory.EMPLOY, middleAddress, PageRequest.of(page, 10));
    }

    @Override
    public Page<Post> getChatPostList(Integer page, String middleAddress) {

        return postRepository
                .findAllByCategoryAndUser_AddressContainingOrderByCreatedAtDesc(PostCategory.CHAT, middleAddress, PageRequest.of(page, 10));
    }

    @Override
    public Page<Post> getSuggestPostList(Integer page, String middleAddress) {

        if(middleAddress.equals("전체")){
            return postRepository.findAllByCategoryOrderByCreatedAtDesc(PostCategory.SUGGEST, PageRequest.of(page, 10));
        } else{
            return postRepository
                    .findAllByCategoryAndUser_AddressContainingOrderByCreatedAtDesc(PostCategory.SUGGEST, middleAddress, PageRequest.of(page, 10));

        }
    }

    @Override
    public Page<Post> getHotPostList(Integer page) {

        return postRepository.findAllByOrderByPostLikeListDesc(PageRequest.of(page, 10));
    }

    @Override
    public Page<Post> getUserPostListByUserId(long userId, Integer page) {
        return postRepository.findAllByUserId(userId, PageRequest.of(page, 10));
    }
}
