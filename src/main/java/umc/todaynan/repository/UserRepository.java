package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.todaynan.domain.entity.User.User.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickname);
}
