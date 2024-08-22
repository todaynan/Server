package umc.todaynan.service.UserBlockingService;

import umc.todaynan.web.dto.UserDTO.UserRequestDTO;

public interface UserBlockingCommandService {
    void user1BlockUser2ByUserId(long userId1, UserRequestDTO.UserGeneralRequestDTO Nickname);
}
