package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.todaynan.domain.entity.User.UserBlocking.UserBlocking;

public interface UserBlockingRepository extends JpaRepository<UserBlocking, Long> {
}
