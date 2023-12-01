package app.api.product.response;

import app.core.rds.entity.product.Product;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class ProductPathResultDto implements Serializable {
    private Long productId; // 상품 ID

    public static ProductPathResultDto of(Product product) {
        return ProductPathResultDto.builder()
                .productId(product.getId())
                .build();
    }
}