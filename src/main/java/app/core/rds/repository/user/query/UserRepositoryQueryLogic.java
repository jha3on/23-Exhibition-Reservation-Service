package app.core.rds.repository.user.query;

import app.core.rds.entity.user.QUser;
import app.core.rds.entity.user.User;
import app.core.rds.entity.user.type.UserSearchType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryQueryLogic implements UserRepositoryQuery {
    private final JPAQueryFactory query;
    private final QUser user = QUser.user;

    @Override
    @Transactional(readOnly = true)
    public Boolean existsBy(String userSearch, UserSearchType userSearchType) {
        var expressionSet = switch (userSearchType) {
            case EMAIL -> user.email.eq(userSearch);
            case CONTACT -> user.contact.eq(userSearch);
        };

        var userGet = query.selectOne().from(user)
                .where(expressionSet)
                .fetchFirst();

        return userGet != null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getBy(Long userId) {
        var userGet = query.selectFrom(user)
                .where(user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(userGet);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getBy(String userEmail, String userPassword) {
        var userGet = query.selectFrom(user)
                .where(user.email.eq(userEmail),
                        user.password.eq(userPassword))
                .fetchOne();

        return Optional.ofNullable(userGet);
    }
}