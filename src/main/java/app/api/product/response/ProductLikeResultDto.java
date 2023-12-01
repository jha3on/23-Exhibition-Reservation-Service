package app.api.product.response;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class ProductLikeResultDto implements Serializable {
    private Boolean productResult;

    public static ProductLikeResultDto of(Boolean productResult) {
        return ProductLikeResultDto.builder()
                .productResult(productResult)
                .build();
    }
}