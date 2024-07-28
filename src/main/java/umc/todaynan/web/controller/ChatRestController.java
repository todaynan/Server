package umc.todaynan.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.SuccessStatus;
import umc.todaynan.converter.ChatConverter;
import umc.todaynan.converter.PostConverter;
import umc.todaynan.domain.entity.Chat.Chat;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.service.ChatService.ChatCommandService;
import umc.todaynan.web.dto.ChatDTO.ChatRequestDTO;
import umc.todaynan.web.dto.ChatDTO.ChatResponseDTO;
import umc.todaynan.web.dto.PostDTO.PostRequestDTO;
import umc.todaynan.web.dto.PostDTO.PostResponseDTO;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private static final Logger log = LoggerFactory.getLogger(ChatRestController.class);
    private final TokenService tokenService;
    private final ChatCommandService chatCommandService;

    @Operation(summary = "쪽지 보내기 API")
    @PostMapping("")
    public ApiResponse<ChatResponseDTO.CreateChatDTO> createChat(HttpServletRequest httpServletRequest,
                                                                 @RequestBody ChatRequestDTO.CreateChatDTO request){
        String givenToken = tokenService.getJwtFromHeader(httpServletRequest);
        String email = tokenService.getUid(givenToken);

        Chat newChat = chatCommandService.createChat(request, email);

        return ApiResponse.onSuccess(ChatConverter.toCreateChatDTO(newChat));
    }
}
