package umc.todaynan.web.dto.ChatDTO;

import lombok.Getter;

public class ChatRoomRequestDTO {

    @Getter
    public static class CreateChatRoomDTO{
        Long senderUserId;
        Long receiveUserId;
    }
}
