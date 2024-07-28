package umc.todaynan.converter;

import umc.todaynan.domain.entity.Chat.Chat;
import umc.todaynan.domain.entity.Chat.ChatRoom;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.web.dto.ChatDTO.ChatRequestDTO;
import umc.todaynan.web.dto.ChatDTO.ChatResponseDTO;
import umc.todaynan.web.dto.ChatDTO.ChatRoomRequestDTO;

public class ChatRoomConverter {

    public static ChatRoom toChatRoom(User receiveUser, User sendUser){
        return ChatRoom.builder()
                .sendUser(sendUser)
                .receiveUser(receiveUser)
                .build();
    }

}
