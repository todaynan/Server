package umc.todaynan.web.dto.ChatDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.todaynan.web.dto.PostDTO.PostResponseDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ChatResponseDTO {

    // 쪽지 쓰기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateChatDTO {
        Long chatId;
        Long chatRoomId;
        Long userId;
        String userNickname;
        String content;
        LocalDateTime createAt;
    }

    // 쪽지 불러오기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatListDTO {
        List<ChatResponseDTO.ChatDTO> chatList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatDTO {
        Long chatId;
        Long chatRoomId;
        Long userId;
        Boolean isMine;
        String userNickname;
        String content;
        LocalDateTime createAt;
    }

    // 쪽지함 미리보기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomListDTO {
        List<ChatResponseDTO.ChatRoomDTO> chatRoomList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    // 쪽지함
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatRoomDTO {
        Long chatRoomId;
        String sendUserNickname;
        String lastChatContent;
        LocalDateTime lastChatCreateAt;
    }
}
