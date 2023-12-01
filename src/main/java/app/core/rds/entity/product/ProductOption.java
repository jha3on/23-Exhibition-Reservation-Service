package app.core.rds.entity.product;

import app.core.rds.AbstractEntity;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;

/**
 * 상품 옵션
 */
@Getter
@Entity
@Table(
    name = "tb_product_option",
    indexes = {
        // @Index(name = "tb_product_option_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_product_option_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 옵션 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product; // 상품

    @Column(nullable = false)
    private String name; // 상품 옵션 이름

    @Column(nullable = false)
    private Integer price; // 상품 옵션 금액

    @Column(nullable = false)
    private Integer quantity; // 상품 옵션 개수

    @Builder(access = AccessLevel.PUBLIC)
    public ProductOption(Long id, Product product, String name, Integer price, Integer quantity) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static ProductOption of(Product product, String name, Integer price, Integer quantity) {
        return ProductOption.builder()
                .product(product)
                .name(name)
                .price(price)
                .quantity(quantity)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public ProductOption updateQuantity(int quantity) {
        var temp = this.quantity + quantity;
        if (temp < 0) {
            throw AppException.of400(AppExceptionType.PRODUCT_OPTION_STOCK_ERROR);
        } else this.quantity = temp;

        return this;
    }
}