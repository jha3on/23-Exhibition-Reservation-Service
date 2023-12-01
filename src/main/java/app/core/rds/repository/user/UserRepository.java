package app.core.rds.repository.user;

import app.core.rds.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // ...
}