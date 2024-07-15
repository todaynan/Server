package umc.todaynan.domain.entity.User.UserPrefer;

import jakarta.persistence.*;
import lombok.*;
import umc.todaynan.domain.common.DateBaseEntity;
import umc.todaynan.domain.entity.User.User.User;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserPrefer extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_prefer_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
