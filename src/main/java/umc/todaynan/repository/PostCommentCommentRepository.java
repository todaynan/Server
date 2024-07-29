package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.todaynan.domain.entity.Post.PostCommentComment.PostCommentComment;

public interface PostCommentCommentRepository extends JpaRepository<PostCommentComment, Long> {
}
