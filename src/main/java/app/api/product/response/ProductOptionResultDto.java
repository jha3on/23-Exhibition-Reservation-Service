package app.api.product.response;

import app.core.rds.entity.product.ProductOption;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder
public class ProductOptionResultDto implements Serializable {
    private Long productOptionId;
    private String productOptionName;
    private Integer productOptionPrice;

    public static ProductOptionResultDto of(ProductOption productOption) {
        return ProductOptionResultDto.builder()
                .productOptionId(productOption.getId())
                .productOptionName(productOption.getName())
                .productOptionPrice(productOption.getPrice())
                .build();
    }

    public static List<ProductOptionResultDto> of(List<ProductOption> productOptions) {
        return productOptions.stream()
                .map(ProductOptionResultDto::of)
                .collect(Collectors.toList());
    }
}