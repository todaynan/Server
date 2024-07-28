package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.todaynan.domain.entity.Chat.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {


}
