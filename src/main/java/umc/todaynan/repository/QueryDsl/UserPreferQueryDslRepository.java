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

import static umc.todaynan.domain.entity.User.User.QUser.user;
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

    public void changePreferList(long userId, List<Integer> interests) {
        query.delete(userPrefer)
                .where(userPrefer.user.id.eq(userId))
                .execute();
        log.info("[Repository - UserPreferQueryDslRepository] {}의 관심사 모두 제거", userId);

        for (Integer interest : interests) {
            UserPrefer build = UserPrefer.builder()
                    .preferCategory(
                            query.select(preferCategory)
                                    .from(preferCategory)
                                    .where(preferCategory.id.eq(Long.valueOf(interest)))
                                    .fetchOne())
                    .user(
                            query.select(user)
                                    .from(user)
                                    .where(user.id.eq(userId))
                                    .fetchOne())
                    .build();
            em.persist(build);
        }
        log.info("[Repository - UserPreferQueryDslRepository] 유저 관심사 변경 Repository 성공");
    }

}
