package umc.todaynan.domain.entity.Chat;

import jakarta.persistence.*;
import lombok.*;
import umc.todaynan.domain.common.DateBaseEntity;
import umc.todaynan.domain.entity.User.User.User;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoom extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "send_user_id")
    private User sendUser;

    @ManyToOne
    @JoinColumn(name = "receive_user_id")
    private User receiveUser;
    //문제

    // ChatRoom과 연결된 Chat들
    @OneToMany(mappedBy = "chatRoom")
    private List<Chat> chats;

}
