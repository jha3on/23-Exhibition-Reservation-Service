package app.core.rds.entity.reservation;

import app.core.rds.entity.product.Product;
import app.core.rds.entity.product.ProductOption;
import app.core.rds.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;

/**
 * 예매 옵션
 */
@Getter
@Entity
@Table(
    name = "tb_reservation_option",
    indexes = {
        // @Index(name = "tb_reservation_option_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_reservation_option_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationOption extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예매 옵션 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product; // 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProductOption productOption; // 상품 옵션

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Reservation reservation; // 예매

    @Column(nullable = false)
    private Integer price; // 예매 가격

    @Column(nullable = false)
    private Integer quantity; // 예매 수량

    @Builder(access = AccessLevel.PUBLIC)
    public ReservationOption(Long id, Product product, ProductOption productOption, Reservation reservation, Integer price, Integer quantity) {
        this.id = id;
        this.product = product;
        this.productOption = productOption;
        this.reservation = reservation;
        this.price = price;
        this.quantity = quantity;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static ReservationOption of(Product product, ProductOption productOption, Reservation reservation, Integer quantity) {
        return ReservationOption.builder()
                .product(product)
                .productOption(productOption)
                .reservation(reservation)
                .price(productOption.getPrice())
                .quantity(quantity)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public Integer getPriceSum() {
        return this.price * this.quantity;
    }

    // -----------------------------------------------------------------------------------------------------------------
}