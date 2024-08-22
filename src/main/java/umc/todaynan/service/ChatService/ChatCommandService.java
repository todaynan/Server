package umc.todaynan.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import umc.todaynan.domain.entity.Chat.Chat;
import umc.todaynan.domain.entity.Chat.ChatRoom;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.web.dto.ChatDTO.ChatRequestDTO;

import java.util.List;

public interface ChatCommandService {

    Chat createChat(ChatRequestDTO.CreateChatDTO request, String userEmail);

    ChatRoom createChatRoom(User sendUser, Long receiveUserId);

    Page<Chat> getChatList(Integer page, Long chatRoomId);

    List<ChatRoom> getChatRoomList(User user);
}
