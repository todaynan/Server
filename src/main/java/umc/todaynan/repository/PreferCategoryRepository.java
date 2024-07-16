package umc.todaynan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.todaynan.domain.entity.User.UserPrefer.PreferCategory;

public interface PreferCategoryRepository extends JpaRepository<PreferCategory, Long> {
}
