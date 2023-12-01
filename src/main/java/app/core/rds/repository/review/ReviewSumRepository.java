package app.core.rds.repository.review;

import app.core.rds.entity.review.ReviewSum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewSumRepository extends JpaRepository<ReviewSum, Long> {
    // ...
}