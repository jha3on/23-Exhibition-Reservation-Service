package app.core.rds.repository.review.query;

import app.core.rds.entity.product.QProduct;
import app.core.rds.entity.review.QReview;
import app.core.rds.entity.review.QReviewSum;
import app.core.rds.entity.review.Review;
import app.core.rds.entity.review.ReviewSum;
import app.core.rds.entity.user.QUser;
import app.share.app.utility.JpaUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReviewRepositoryQueryLogic implements ReviewRepositoryQuery {
    private final JPAQueryFactory query;
    private final QUser user = QUser.user;
    private final QReview review = QReview.review;
    private final QProduct product = QProduct.product;
    private final QReviewSum reviewSum = QReviewSum.reviewSum;

    @Override
    @Transactional(readOnly = true)
    public Optional<Review> getBy(Long userId, Long productId) {
        var reviewGet = query.selectFrom(review)
                .join(review.user, user)
                .join(review.product, product)
                .where(review.user.id.eq(userId),
                        review.product.id.eq(productId))
                .fetchOne();

        return Optional.ofNullable(reviewGet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> getAllBy(Long productId) {
        var reviewGets = query.selectFrom(review)
                .join(review.product, product)
                .where(review.product.id.eq(productId))
                .orderBy(review.id.desc())
                .fetch();

        return JpaUtils.getOrEmpty(reviewGets);
    }

    @Override
    public List<Review> getAllUserBy(Long userId) {
        var reviewGets = query.selectFrom(review)
                .join(review.user, user)
                .where(review.user.id.eq(userId))
                .orderBy(review.id.desc())
                .fetch();

        return JpaUtils.getOrEmpty(reviewGets);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewSum> getBy(Long productId) {
        var reviewSumGet = query.selectFrom(reviewSum)
                .join(reviewSum.product, product)
                .where(reviewSum.product.id.eq(productId))
                .fetchOne();

        return Optional.ofNullable(reviewSumGet);
    }
}