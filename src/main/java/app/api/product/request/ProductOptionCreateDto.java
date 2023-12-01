package app.api.product.request;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductOptionCreateDto implements Serializable {
    private String productOptionName;
    private Integer productOptionPrice;
    private Integer productOptionQuantity;
}