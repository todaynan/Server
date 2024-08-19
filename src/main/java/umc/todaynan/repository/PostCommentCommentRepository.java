package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.todaynan.domain.entity.Post.PostCommentComment.PostCommentComment;

import java.util.List;

public interface PostCommentCommentRepository extends JpaRepository<PostCommentComment, Long> {
    @Query("SELECT pcc.post.id FROM PostCommentComment pcc WHERE pcc.user.id = :userId")
    List<Long> findPostIdsByUserId(@Param("userId") Long userId);
}
