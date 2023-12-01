package app.core.rds.repository.promotion.query;

import app.core.rds.entity.promotion.Promotion;
import app.core.rds.entity.promotion.QPromotion;
import app.core.rds.entity.promotion.type.PromotionGroupType;
import app.share.app.utility.JpaUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PromotionRepositoryQueryLogic implements PromotionRepositoryQuery {
    private final JPAQueryFactory query;
    private final QPromotion promotion = QPromotion.promotion;

    @Override
    @Transactional(readOnly = true)
    public List<Promotion> getAllBy(PromotionGroupType promotionGroupType) {
        var promotionGets = query.selectFrom(promotion)
                .where(promotion.groupType.eq(promotionGroupType))
                .orderBy(promotion.id.desc())
                .fetch();

        return JpaUtils.getOrEmpty(promotionGets);
    }
}