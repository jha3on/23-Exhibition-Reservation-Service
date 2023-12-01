package app.core.rds.entity.review;

import app.core.rds.entity.product.Product;
import app.core.rds.entity.user.User;
import app.core.rds.entity.review.type.ReviewStatusType;
import app.core.rds.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.*;
import java.io.Serializable;

/**
 * 리뷰
 */
@Getter
@Entity
@Table(
    name = "tb_review",
    indexes = {
        // @Index(name = "tb_review_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        // @UniqueConstraint(name = "tb_review_uk_1", columnNames = {"xxx"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 리뷰 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user; // 회원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product; // 상품

    @Column(nullable = false)
    private Integer score; // 리뷰 점수

    @Column(nullable = false)
    private String content; // 리뷰 내용

    @Enumerated(value = EnumType.STRING)
    private ReviewStatusType statusType; // 리뷰 상태 유형

    @Builder(access = AccessLevel.PUBLIC)
    public Review(Long id, User user, Product product, Integer score, String content, ReviewStatusType statusType) {
        this.id = id;
        this.user = user;
        this.score = score;
        this.content = content;
        this.product = product;
        this.statusType = statusType;
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static Review of(User user, Product product, Integer score, String content) {
        return Review.builder()
                .user(user)
                .score(score)
                .content(content)
                .product(product)
                .statusType(ReviewStatusType.CREATED)
                .build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    public Review updateScore(Integer score) {
        this.score = score;
        return this;
    }

    public Review updateContent(String content) {
        this.content = content;
        return this;
    }

    // -----------------------------------------------------------------------------------------------------------------
}