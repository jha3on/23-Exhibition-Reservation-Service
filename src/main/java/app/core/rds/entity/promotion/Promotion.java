package app.core.rds.entity.promotion;

import app.core.rds.entity.product.Product;
import app.core.rds.AbstractEntity;
import app.core.rds.entity.promotion.type.PromotionGroupType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;

/**
 * 프로모션
 */
@Getter
@Entity
@Table(
    name = "tb_promotion",
    indexes = {
        // @Index(name = "tb_promotion_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_promotion_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 프로모션 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product; // 상품

    @Enumerated(value = EnumType.STRING)
    private PromotionGroupType groupType; // 프로모션 그룹 유형

    @Builder(access = AccessLevel.PUBLIC)
    public Promotion(Long id, Product product, PromotionGroupType groupType) {
        this.id = id;
        this.product = product;
        this.groupType = groupType;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static Promotion of(Product product, PromotionGroupType groupType) {
        return Promotion.builder()
                .product(product)
                .groupType(groupType)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------
}