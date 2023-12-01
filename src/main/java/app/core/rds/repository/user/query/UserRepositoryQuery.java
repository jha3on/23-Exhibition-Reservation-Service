package app.core.rds.repository.user.query;

import app.core.rds.entity.user.User;
import app.core.rds.entity.user.type.UserSearchType;
import java.util.Optional;

public interface UserRepositoryQuery {

    /** [Fix] User 객체 중복 조회 */
    Boolean existsBy(String userSearch, UserSearchType userSearchType);

    /** [Fix] User 객체 조회 */
    Optional<User> getBy(Long userId);

    /** [Fix] User 객체 조회 */
    Optional<User> getBy(String userEmail, String userPassword);


}