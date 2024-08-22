package umc.todaynan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.todaynan.domain.entity.Chat.Chat;
import umc.todaynan.domain.entity.Chat.ChatRoom;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE c.chatRoom = :chatRoom ORDER BY c.createdAt DESC")
    Page<Chat> findChatsByChatRoom(@Param("chatRoom") ChatRoom chatRoom, PageRequest pageRequest);
}
