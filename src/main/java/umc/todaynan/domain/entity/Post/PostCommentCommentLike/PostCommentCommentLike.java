package umc.todaynan.domain.entity.Post.PostCommentCommentLike;

import jakarta.persistence.*;
import lombok.*;
import umc.todaynan.domain.common.DateBaseEntity;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.Post.PostCommentComment.PostCommentComment;
import umc.todaynan.domain.entity.User.User.User;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostCommentCommentLike extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_comment_comment_id")
    private PostCommentComment postCommentComment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
