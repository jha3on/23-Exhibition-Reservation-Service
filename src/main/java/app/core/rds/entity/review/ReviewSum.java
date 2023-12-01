package app.core.rds.entity.review;

import app.core.rds.entity.product.Product;
import app.core.rds.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;

/**
 * 리뷰 합계
 */
@Getter
@Entity
@Table(
    name = "tb_review_sum",
    indexes = {
        // @Index(name = "tb_review_sum_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_review_sum_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewSum extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 리뷰 합계 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product; // 상품

    @Column(nullable = false)
    private Integer scoreSum; // 리뷰 총 점수

    @Column(nullable = false)
    private Integer scoreCount; // 리뷰 총 개수

    @Builder(access = AccessLevel.PUBLIC)
    public ReviewSum(Long id, Product product, Integer scoreSum, Integer scoreCount) {
        this.id = id;
        this.product = product;
        this.scoreSum = scoreSum;
        this.scoreCount = scoreCount;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static ReviewSum of(Product product) {
        return ReviewSum.builder()
                .product(product)
                .scoreSum(0)
                .scoreCount(0)
                .build();
    }

    public static ReviewSum of(Review review, Product product) {
        return ReviewSum.builder()
                .product(product)
                .scoreSum(review.getScore())
                .scoreCount(1)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public ReviewSum plusScore(int score) {
        this.scoreSum += score;
        this.scoreCount += 1;
        return this;
    }

    public ReviewSum minusScore(int score) {
        this.scoreSum -= score;
        this.scoreCount -= 1;
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
}