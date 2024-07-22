package umc.todaynan.repository.QueryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.todaynan.domain.entity.User.User.QUser;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.entity.User.UserPrefer.PreferCategory;
import umc.todaynan.domain.entity.User.UserPrefer.UserPrefer;

import java.util.List;

import static umc.todaynan.domain.entity.User.UserPrefer.QPreferCategory.preferCategory;
import static umc.todaynan.domain.entity.User.UserPrefer.QUserPrefer.userPrefer;

@Slf4j
@Repository
public class UserPreferQueryDslRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public UserPreferQueryDslRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public void changePreferList(long userId, List<String> interests) {
        query.delete(userPrefer)
                .where(userPrefer.user.id.eq(userId))
                .execute();
        log.info("[Repository - UserPreferQueryDslRepository] {}의 관심사 모두 제거", userId);

        User user = query.selectFrom(QUser.user)
                .where(QUser.user.id.eq(userId))
                .fetchOne();

        for (String interest : interests) {
            Long preferCategoryId = query.select(preferCategory.id)
                    .from(preferCategory)
                    .where(preferCategory.title.eq(interest))
                    .fetchOne();


            if (preferCategoryId != null) {
                PreferCategory newCategory = query.select(preferCategory)
                        .where(preferCategory.id.eq(preferCategoryId))
                        .fetchOne();

                UserPrefer newUserPrefer = UserPrefer.builder()
                        .user(user)
                        .preferCategory(newCategory)
                        .build();

                em.persist(newUserPrefer);
            }
        }
        log.info("[Repository - UserPreferQueryDslRepository] 유저 관심사 변경 Repository 성공");
    }

}
