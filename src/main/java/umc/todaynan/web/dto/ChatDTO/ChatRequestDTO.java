package umc.todaynan.web.dto.ChatDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import umc.todaynan.domain.enums.PostCategory;

public class ChatRequestDTO {

    @Getter
    public static class CreateChatDTO{
        @NotBlank
        Long receiveUserId;

        @NotBlank
        Long chatRoomId;

        @NotBlank
        String content;
    }
}
