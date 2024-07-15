package umc.todaynan.domain.entity.User.UserBlocking;

import jakarta.persistence.*;
import lombok.*;
import umc.todaynan.domain.common.DateBaseEntity;
import umc.todaynan.domain.entity.User.User.User;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserBlocking extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "blocking_user_id")
    private User blockingUser;

    @ManyToOne
    @JoinColumn(name = "blocked_user_id")
    private User blockedUser;
}
