package app.core.rds.repository.promotion.query;

import app.core.rds.entity.promotion.Promotion;
import app.core.rds.entity.promotion.type.PromotionGroupType;
import java.util.List;

public interface PromotionRepositoryQuery {

    /** [Fix] Promotion 컬렉션 조회 */
    List<Promotion> getAllBy(PromotionGroupType promotionGroupType);
}