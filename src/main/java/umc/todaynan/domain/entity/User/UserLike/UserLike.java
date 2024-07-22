package umc.todaynan.domain.entity.User.UserLike;

import jakarta.persistence.*;
import lombok.*;
import umc.todaynan.domain.common.DateBaseEntity;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.enums.PlaceCategory;
import umc.todaynan.domain.enums.UserStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLike  extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String placeAddress;

    @Column(length = 100)
    private String description;

    @Column(nullable = false, length = 15)
    private String title;

    @Column(nullable = false, length = 300)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceCategory category;


}
