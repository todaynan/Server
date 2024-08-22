package umc.todaynan.converter;

import org.springframework.data.domain.Page;
import umc.todaynan.domain.entity.Chat.Chat;
import umc.todaynan.domain.entity.Chat.ChatRoom;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.web.dto.ChatDTO.ChatRequestDTO;
import umc.todaynan.web.dto.ChatDTO.ChatResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public static ChatResponseDTO.ChatDTO toChatDTO(Chat chat) {
        return ChatResponseDTO.ChatDTO.builder()
                .chatId(chat.getId())
                .chatRoomId(chat.getChatRoom().getId())
                .userId(chat.getUser().getId())
                .userNickname(chat.getUser().getNickName())
                .content(chat.getContent())
                .createAt(chat.getCreatedAt())
                .build();
    }

    public static ChatResponseDTO.ChatListDTO toChatListDTO(Page<Chat> chatList) {
        List<ChatResponseDTO.ChatDTO> chatDTOList = chatList.stream()
                .map(ChatConverter::toChatDTO).collect(Collectors.toList());

        return ChatResponseDTO.ChatListDTO.builder()
                .isLast(chatList.isLast())
                .isFirst(chatList.isFirst())
                .totalPage(chatList.getTotalPages())
                .totalElements(chatList.getTotalElements())
                .listSize(chatDTOList.size())
                .chatList(chatDTOList)
                .build();
    }
}
