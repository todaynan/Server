package umc.todaynan.converter;

import umc.todaynan.domain.entity.Chat.Chat;
import umc.todaynan.domain.entity.Chat.ChatRoom;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.web.dto.ChatDTO.ChatRequestDTO;
import umc.todaynan.web.dto.ChatDTO.ChatResponseDTO;
import umc.todaynan.web.dto.ChatDTO.ChatRoomRequestDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChatRoomConverter {

    public static ChatRoom toChatRoom(User receiveUser, User sendUser){
        return ChatRoom.builder()
                .sendUser(sendUser)
                .receiveUser(receiveUser)
                .build();
    }

    public static ChatResponseDTO.ChatRoomDTO toChatRoomDTO(ChatRoom chatRoom){
        // 가장 최신 Chat을 찾기 위한 스트림 사용
        Chat latestChat = chatRoom.getChats().stream()
                .max(Comparator.comparing(Chat::getCreatedAt))
                .orElse(null);  // 채팅이 없을 경우 null 처리

        return ChatResponseDTO.ChatRoomDTO.builder()
                .chatRoomId(chatRoom.getId())
                .sendUserNickname(latestChat.getUser().getNickName())
                .lastChatContent(latestChat.getContent())
                .lastChatCreateAt(latestChat.getCreatedAt())
                .build();
    }

    public static ChatResponseDTO.ChatRoomListDTO toChatRoomListDTO(List<ChatRoom> chatRoomList){

        List<ChatResponseDTO.ChatRoomDTO> chatRoomDTOList = chatRoomList.stream()
                .map(ChatRoomConverter::toChatRoomDTO).collect(Collectors.toList());

        return ChatResponseDTO.ChatRoomListDTO.builder()
                .chatRoomList(chatRoomDTOList)
                .build();
    }

}
