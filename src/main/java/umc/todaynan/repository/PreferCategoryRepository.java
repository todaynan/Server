package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.entity.User.UserLike.UserLike;
import umc.todaynan.domain.entity.User.UserPrefer.PreferCategory;
import umc.todaynan.domain.entity.User.UserPrefer.UserPrefer;

import java.util.List;

public interface PreferCategoryRepository extends JpaRepository<PreferCategory, Long> {
    @Query("SELECT pc.title FROM PreferCategory pc WHERE pc.id IN (SELECT up.preferCategory.id FROM UserPrefer up WHERE up IN :userPreferList)")
    List<String> findTitlesByUserPrefer(@Param("userPreferList") List<UserPrefer> userPreferList);


}
