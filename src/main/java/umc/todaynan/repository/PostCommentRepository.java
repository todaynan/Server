package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.todaynan.domain.entity.Post.PostComment.PostComment;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    Optional<PostComment> findById(Long Id);
    List<PostComment> findByPostId(Long postId);
}
