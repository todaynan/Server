package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.todaynan.domain.entity.Post.PostCommentLike.PostCommentLike;

import java.util.Optional;

public interface PostCommentLikeRepository extends JpaRepository<PostCommentLike, Integer> {
    Optional<PostCommentLike> findByUserIdAndPostCommentId(Long userId, Long postCommentId);
    @Query("SELECT COUNT(pcl) FROM PostCommentLike pcl WHERE pcl.postComment.id = :post_comment_id")
    Long countByPostId(@Param("post_comment_id") Long post_comment_id);
}
