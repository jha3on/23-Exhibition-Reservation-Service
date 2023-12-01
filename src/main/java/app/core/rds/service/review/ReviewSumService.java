package app.core.rds.service.review;

import app.core.rds.entity.product.Product;
import app.core.rds.entity.review.Review;
import app.core.rds.entity.review.ReviewSum;
import app.core.rds.repository.review.ReviewSumRepository;
import app.core.rds.repository.review.query.ReviewRepositoryQuery;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewSumService {
    private final ReviewSumRepository reviewSumRepository;
    private final ReviewRepositoryQuery reviewRepositoryQuery;

    /**
     * [Fix] ReviewSum 객체를 저장한다.
     */
    @Transactional
    public ReviewSum plusBy(Review review, Product product) {
        var reviewSumOpt = reviewRepositoryQuery.getBy(product.getId());

        if (reviewSumOpt.isEmpty()) {
            var reviewSum = createBy(review, product);
            return reviewSumRepository.save(reviewSum);
        } else {
            var reviewSum = reviewSumOpt.get();
            return reviewSum.plusScore(review.getScore());
        }
    }

    /**
     * [Fix] ReviewSum 객체를 조회한다.
     */
    @Transactional(readOnly = true)
    public ReviewSum getBy(Product product) {
        var reviewSumOpt = reviewRepositoryQuery.getBy(product.getId());

        return reviewSumOpt.orElseGet(() -> createBy(product));
    }

    /**
     * [Fix] ReviewSum 객체를 조회한다.
     */
    @Transactional(readOnly = true)
    public ReviewSum getOrBy(Product product) {
        var reviewSumOpt = reviewRepositoryQuery.getBy(product.getId());

        if (reviewSumOpt.isEmpty()) {
            throw AppException.of404(AppExceptionType.REVIEW_SUM_GET_ERROR);
        } else return reviewSumOpt.get();
    }

    private ReviewSum createBy(Product product) {
        return ReviewSum.of(product);
    }

    private ReviewSum createBy(Review review, Product product) {
        return ReviewSum.of(review, product);
    }
}