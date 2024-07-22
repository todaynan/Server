package umc.todaynan.repository.QueryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static umc.todaynan.domain.entity.User.User.QUser.user;

@Slf4j
@Repository
public class UserBlockingQueryDslRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public UserBlockingQueryDslRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Long findUserIdByUserNickName(String nickname) {
        return query.select(user.id)
                .from(user)
                .where(user.nickName.eq(nickname))
                .fetchOne();
    }

}
