package umc.todaynan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.enums.PostCategory;
import umc.todaynan.domain.entity.User.User.User;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post save(Post post); //게시글 저장
    Optional<Post> findByIdAndUserId(Long id, Long user_id); //해당 유저가 쓴 게시글이 있는지 확인
    Page<Post> findAllByCategoryOrderByCreatedAtDesc(PostCategory category, PageRequest pageRequest);
    Page<Post> findAllByCategoryAndUser_AddressContainingOrderByCreatedAtDesc(PostCategory category, String address, PageRequest pageRequest);
    Page<Post> findAllByOrderByPostLikeListDesc(PageRequest pageRequest);
}
