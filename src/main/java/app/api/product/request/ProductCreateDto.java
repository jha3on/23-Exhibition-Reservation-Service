package app.api.product.request;

import app.core.rds.entity.product.type.ProductGroupSubtype;
import app.core.rds.entity.product.type.ProductGroupType;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductCreateDto implements Serializable {
    private Long placeId;
    private String productName;
    private LocalDateTime productDateTo;
    private LocalDateTime productDateFrom;
    private ProductGroupType productGroupType;
    private ProductGroupSubtype productGroupSubtype;
    private List<ProductOptionCreateDto> productOptions;
}