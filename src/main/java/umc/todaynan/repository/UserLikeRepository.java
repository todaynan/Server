package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.entity.User.UserLike.UserLike;
import umc.todaynan.domain.enums.PlaceCategory;

import java.util.List;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    List<UserLike> findAllByUser(User user);
    Integer deleteUserLikeByIdAndUser(Long likeId, User user);

    Boolean existsByTitleAndUserAndCategory(String title, User user, PlaceCategory placeCategory);

    void deleteByUserId(Long userId);
}
