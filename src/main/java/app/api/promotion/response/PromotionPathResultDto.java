package app.api.promotion.response;

import app.core.rds.entity.promotion.Promotion;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class PromotionPathResultDto implements Serializable {
    private Long promotionId; // 프로모션 ID

    public static PromotionPathResultDto of(Promotion promotion) {
        return PromotionPathResultDto.builder()
                .promotionId(promotion.getId())
                .build();
    }
}