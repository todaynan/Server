package umc.todaynan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.todaynan.domain.entity.Chat.ChatRoom;
import umc.todaynan.domain.entity.User.User.User;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findById(Long id);

    @Query("SELECT cr FROM ChatRoom cr " +
            "LEFT JOIN cr.chats c " +
            "WHERE cr.sendUser.id = :userId OR cr.receiveUser.id = :userId " +
            "GROUP BY cr.id " +
            "ORDER BY MAX(c.createdAt) DESC")
    List<ChatRoom> findChatRoomsByUserId(@Param("userId") Long userId);
}
