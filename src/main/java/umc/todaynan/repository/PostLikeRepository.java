package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.Post.PostLike.PostLike;
import umc.todaynan.domain.entity.User.User.User;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    PostLike save(PostLike postLike);
    Boolean existsByUserAndPost(User user, Post post);
    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.id = :post_id")
    Long countByPostId(@Param("post_id") Long post_id);
}
