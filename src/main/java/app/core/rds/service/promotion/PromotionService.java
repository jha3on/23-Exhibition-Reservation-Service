package app.core.rds.service.promotion;

import app.api.promotion.request.PromotionCreateDto;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.promotion.Promotion;
import app.core.rds.entity.promotion.type.PromotionGroupType;
import app.core.rds.repository.promotion.PromotionRepository;
import app.core.rds.repository.promotion.query.PromotionRepositoryQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionRepositoryQuery promotionRepositoryQuery;

    /**
     * [Fix] Promotion 객체를 저장한다.
     */
    @Transactional
    public Promotion saveBy(Product product, PromotionCreateDto promotionDto) {
        var promotion = createBy(product, promotionDto);

        return promotionRepository.save(promotion);
    }

    /**
     * [Fix] Promotion 컬렉션을 조회한다.
     */
    @Transactional
    public List<Promotion> getAllBy(PromotionGroupType promotionGroupType) {
        return promotionRepositoryQuery.getAllBy(promotionGroupType);
    }

    private Promotion createBy(Product product, PromotionCreateDto promotionDto) {
        return Promotion.of(product, promotionDto.getPromotionGroupType());
    }
}