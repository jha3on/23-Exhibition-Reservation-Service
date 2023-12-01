package app.api.promotion;

import app.core.rds.entity.promotion.Promotion;
import app.core.rds.service.product.ProductService;
import app.core.rds.service.promotion.PromotionFileService;
import app.core.rds.service.promotion.PromotionService;
import app.api.promotion.request.PromotionCreateDto;
import app.api.promotion.response.PromotionPathResultDto;
import app.api.promotion.response.PromotionResultDto;
import app.core.rds.entity.promotion.type.PromotionGroupType;
import app.share.app.config.redis.RedisCacheKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionApiService {
    private final ProductService productService;
    private final PromotionService promotionService;
    private final PromotionFileService promotionFileService;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 프로모션을 생성한다.
     */
    @Transactional
    public PromotionPathResultDto createPromotionBy(PromotionCreateDto promotionDto, List<MultipartFile> attachments) {
        var product = productService.getBy(promotionDto.getProductId());
        var promotion = promotionService.saveBy(product, promotionDto);
        // var promotionFiles = promotionFileService.saveAllBy(promotion, attachments);

        return PromotionPathResultDto.of(promotion);
    }

    /**
     * 프로모션 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    @Cacheable(value = RedisCacheKey.PROMOTION_LIST, key = "#promotionGroupType", cacheManager = "redisCacheManager")
    public List<PromotionResultDto> getPromotionsBy(PromotionGroupType promotionGroupType) {
        var promotions = promotionService.getAllBy(promotionGroupType);
        var promotionFiles = promotionFileService.getAllBy(getPromotionProductIds(promotions));

        return PromotionResultDto.of(promotions, promotionFiles);
    }

    private List<Long> getPromotionProductIds(List<Promotion> promotions) {
        return promotions.stream()
                .map(promotion -> promotion.getProduct().getId())
                .collect(Collectors.toList());
    }

//    private List<Long> getPromotionIds(List<Promotion> promotions) {
//        return promotions.stream()
//                .map(Promotion::getId)
//                .collect(Collectors.toList());
//    }

    // -----------------------------------------------------------------------------------------------------------------
}