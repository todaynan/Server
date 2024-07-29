package umc.todaynan.service.ChatService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.exception.GeneralException;
import umc.todaynan.apiPayload.exception.handler.UserHandler;
import umc.todaynan.converter.ChatConverter;
import umc.todaynan.converter.ChatRoomConverter;
import umc.todaynan.domain.entity.Chat.Chat;
import umc.todaynan.domain.entity.Chat.ChatRoom;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.repository.ChatRepository;
import umc.todaynan.repository.ChatRoomRepository;
import umc.todaynan.repository.UserRepository;
import umc.todaynan.web.dto.ChatDTO.ChatRequestDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatCommandServiceImpl implements ChatCommandService{

    private static final Logger log = LoggerFactory.getLogger(ChatCommandServiceImpl.class);
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    @Override
    public Chat createChat(ChatRequestDTO.CreateChatDTO request, String userEmail) {

        // todo : user 와 receiver 둘 다 속한 ChatRoom이 있는지 확인하고 없을 때만 ChatRoom 생성

        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserHandler(ErrorStatus.USER_ERROR));

        ChatRoom chatRoom;

        if(request.getChatRoomId() == 0){
            chatRoom = createChatRoom(user, request.getReceiveUserId());

        } else{
            chatRoom = chatRoomRepository.findById(request.getChatRoomId()).orElseThrow(() -> new GeneralException(ErrorStatus.ChatRoom_NOT_FOUND));
        }

        Chat newChat = ChatConverter.toChat(request, chatRoom, user);


        return chatRepository.save(newChat);
    }

    @Transactional
    @Override
    public ChatRoom createChatRoom(User sendUser, Long receiveUserId) {

        User receiveUser = userRepository.findById(receiveUserId).orElseThrow(() -> new UserHandler(ErrorStatus.USER_ERROR));

        ChatRoom newChatRoom = ChatRoomConverter.toChatRoom(receiveUser, sendUser);

        return chatRoomRepository.save(newChatRoom);
    }

    @Override
    public Page<Chat> getChatList(Integer page, Long chatRoomId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new GeneralException(ErrorStatus.ChatRoom_NOT_FOUND));


        return chatRepository.findChatsByChatRoom(chatRoom, PageRequest.of(page, 30));
    }

    @Override
    public List<ChatRoom> getChatRoomList(User user){

        return chatRoomRepository.findChatRoomsByUserId(user.getId());
    }

}
