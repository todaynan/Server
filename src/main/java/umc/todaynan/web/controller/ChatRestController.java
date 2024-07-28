package umc.todaynan.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.code.status.SuccessStatus;
import umc.todaynan.converter.ChatConverter;
import umc.todaynan.converter.ChatRoomConverter;
import umc.todaynan.converter.PostConverter;
import umc.todaynan.domain.entity.Chat.Chat;
import umc.todaynan.domain.entity.Chat.ChatRoom;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.repository.UserRepository;
import umc.todaynan.service.ChatService.ChatCommandService;
import umc.todaynan.web.dto.ChatDTO.ChatRequestDTO;
import umc.todaynan.web.dto.ChatDTO.ChatResponseDTO;
import umc.todaynan.web.dto.PostDTO.PostRequestDTO;
import umc.todaynan.web.dto.PostDTO.PostResponseDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private static final Logger log = LoggerFactory.getLogger(ChatRestController.class);
    private final TokenService tokenService;
    private final ChatCommandService chatCommandService;
    private final UserRepository userRepository;

    @Operation(summary = "쪽지 보내기 API")
    @PostMapping("")
    public ApiResponse<ChatResponseDTO.CreateChatDTO> createChat(HttpServletRequest httpServletRequest,
                                                                 @RequestBody ChatRequestDTO.CreateChatDTO request){
        String givenToken = tokenService.getJwtFromHeader(httpServletRequest);
        String email = tokenService.getUid(givenToken);

        Chat newChat = chatCommandService.createChat(request, email);

        return ApiResponse.onSuccess(ChatConverter.toCreateChatDTO(newChat));
    }

    @Operation(summary = "쪽지 불러오기 API")
    @GetMapping("")
    public ApiResponse<ChatResponseDTO.ChatListDTO> getChatList(HttpServletRequest httpServletRequest,
                                                                @RequestParam Long chatRoomId,
                                                                @Parameter(description = "페이지 번호(1부터 시작), default: 1 / size = 30")
                                                                    @RequestParam(defaultValue = "1") Integer page){
        String givenToken = tokenService.getJwtFromHeader(httpServletRequest);
        String email = tokenService.getUid(givenToken);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            Page<Chat> chatPage = chatCommandService.getChatList(page-1, chatRoomId);
            return ApiResponse.onSuccess(ChatConverter.toChatListDTO(chatPage));
        }else {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }
    }

    @Operation(summary = "쪽지함 불러오기 API")
    @GetMapping("/room")
    public ApiResponse<ChatResponseDTO.ChatRoomListDTO> getChatRoomList(HttpServletRequest httpServletRequest,
                                                                        @Parameter(description = "페이지 번호(1부터 시작), default: 1 / size = 15")
                                                                        @RequestParam(defaultValue = "1") Integer page){
        String givenToken = tokenService.getJwtFromHeader(httpServletRequest);
        String email = tokenService.getUid(givenToken);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            List<ChatRoom> chatRoomList = chatCommandService.getChatRoomList(user.get());
            return ApiResponse.onSuccess(ChatRoomConverter.toChatRoomListDTO(chatRoomList));
        }else {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }
    }
}
