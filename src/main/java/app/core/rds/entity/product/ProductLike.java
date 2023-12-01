package app.core.rds.entity.product;

import app.core.rds.AbstractLogEntity;
import app.core.rds.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;

/**
 * 관심 상품
 */
@Getter
@Entity
@Table(
    name = "tb_product_like",
    indexes = {
        // @Index(name = "tb_product_like_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_product_like_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductLike extends AbstractLogEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 관심 상품 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user; // 회원 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product; // 상품 ID

    @Builder(access = AccessLevel.PUBLIC)
    public ProductLike(Long id, User user, Product product) {
        this.id = id;
        this.user = user;
        this.product = product;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static ProductLike of(User user, Product product) {
        return ProductLike.builder()
                .user(user)
                .product(product)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------
}