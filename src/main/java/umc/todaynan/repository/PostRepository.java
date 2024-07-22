package umc.todaynan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.enums.PostCategory;

public interface PostRepository extends JpaRepository<Post,Long> {

    Page<Post> findAllByCategoryOrderByCreatedAtDesc(PostCategory category, PageRequest pageRequest);
    Page<Post> findAllByCategoryAndUser_AddressContainingOrderByCreatedAtDesc(PostCategory category, String address, PageRequest pageRequest);
    Page<Post> findAllByOrderByPostLikeListDesc(PageRequest pageRequest);
}
