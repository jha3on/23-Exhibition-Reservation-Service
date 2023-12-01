package app.api.promotion.request;

import app.core.rds.entity.promotion.type.PromotionGroupType;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class PromotionCreateDto implements Serializable {
    private Long productId;
    private PromotionGroupType promotionGroupType;
}