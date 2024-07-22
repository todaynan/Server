package umc.todaynan.service.UserBlockingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.todaynan.apiPayload.exception.UserNotFoundException;
import umc.todaynan.domain.entity.User.UserBlocking.UserBlocking;
import umc.todaynan.repository.QueryDsl.UserBlockingQueryDslRepository;
import umc.todaynan.repository.UserBlockingRepository;
import umc.todaynan.repository.UserRepository;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserBlockingCommandServiceImpl implements UserBlockingCommandService {
    private final UserBlockingQueryDslRepository userBlockingQueryDslRepository;
    private final UserBlockingRepository userBlockingRepository;
    private final UserRepository userRepository;
    @Override
    public void user1BlockUser2ByUserId(long userId1, String Nickname) {
        Long userId2 = userBlockingQueryDslRepository.findUserIdByUserNickName(Nickname);
        if(userId2 == null) {
            log.info("[Repository - user1BlockUser2ByUserId] {}이라는 닉네임을 가진 유저를 찾을 수 없습니다. UserNotFoundException 처리합니다.", Nickname);
            throw new UserNotFoundException("해당 닉네임을 가진 학생이 없습니다.");
        }
        userBlockingRepository.save(
                UserBlocking.builder()
                        .blockingUser(
                                userRepository.findById(userId1).orElseThrow(
                                        () -> new UserNotFoundException("해당 ID를 가진 유저를 찾지 못했습니다.(차단하는 사람의 ID)")

                                )
                        )
                        .blockedUser(
                                userRepository.findById(userId2).orElseThrow(
                                        () -> new UserNotFoundException("해당 ID를 가진 유저를 찾지 못했습니다.(차단당하는 사람의 ID)")
                                )
                        )
                        .build()
        );
        log.info("차단 데이터베이스에 {}번 유저가 {}를 차단한 것으로 저장이 완료되었습니다.", userId1, userId2);
    }
}
