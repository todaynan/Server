package umc.todaynan.domain.entity.Post.PostComment;

import jakarta.persistence.*;
import lombok.*;
import umc.todaynan.domain.common.DateBaseEntity;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.Post.PostCommentComment.PostCommentComment;
import umc.todaynan.domain.entity.Post.PostCommentLike.PostCommentLike;
import umc.todaynan.domain.entity.User.User.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostComment extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 200)
    private String comment;

    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL)
    private List<PostCommentLike> postCommentLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL)
    private List<PostCommentComment> postCommentCommentList = new ArrayList<>();
}
