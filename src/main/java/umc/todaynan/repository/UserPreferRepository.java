package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.entity.User.UserLike.UserLike;
import umc.todaynan.domain.entity.User.UserPrefer.UserPrefer;

import java.util.List;

public interface UserPreferRepository extends JpaRepository<UserPrefer, Long> {
    List<UserPrefer> findAllByUser(User user);
}
