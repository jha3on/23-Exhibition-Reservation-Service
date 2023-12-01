package app.core.rds.repository.promotion;

import app.core.rds.entity.promotion.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    // ...
}