package umc.todaynan.converter;

import umc.todaynan.domain.entity.Chat.Chat;
import umc.todaynan.domain.entity.Chat.ChatRoom;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.web.dto.ChatDTO.ChatRequestDTO;
import umc.todaynan.web.dto.ChatDTO.ChatResponseDTO;

import java.util.Optional;

public class ChatConverter {

    public static Chat toChat(ChatRequestDTO.CreateChatDTO request, ChatRoom chatRoom, User user){
        return Chat.builder()
                .content(request.getContent())
                .user(user)
                .chatRoom(chatRoom)
                .build();

    }
    public static ChatResponseDTO.CreateChatDTO toCreateChatDTO(Chat chat) {
        return ChatResponseDTO.CreateChatDTO.builder()
                .chatId(chat.getId())
                .chatRoomId(chat.getChatRoom().getId())
                .userId(chat.getUser().getId())
                .userNickname(chat.getUser().getNickName())
                .content(chat.getContent())
                .createAt(chat.getCreatedAt())
                .build();
    }
}
