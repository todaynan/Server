package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.todaynan.domain.entity.Post.PostCommentLike.PostCommentLike;

import java.util.Optional;

public interface PostCommentLikeRepository extends JpaRepository<PostCommentLike, Integer> {
    Optional<PostCommentLike> findByUserIdAndPostCommentId(Long userId, Long postCommentId);
}
