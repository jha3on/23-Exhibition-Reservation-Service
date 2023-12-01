package app.core.rds.service.review;

import app.api.review.request.ReviewCreateDto;
import app.core.rds.entity.product.Product;
import app.core.rds.entity.review.Review;
import app.core.rds.entity.user.User;
import app.core.rds.repository.review.ReviewRepository;
import app.core.rds.repository.review.query.ReviewRepositoryQuery;
import app.share.app.exception.AppException;
import app.share.app.exception.AppExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewRepositoryQuery reviewRepositoryQuery;

    /**
     * [Fix] Review 객체를 저장한다.
     */
    @Transactional
    public Review saveBy(User user, Product product, ReviewCreateDto reviewDto) {
        var review = createBy(user, product, reviewDto);

        return reviewRepository.save(review);
    }

    /**
     * [Fix] Review 객체를 조회한다.
     */
    @Transactional(readOnly = true)
    public Review getBy(Long userId, Long productId) {
        var reviewOpt = reviewRepositoryQuery.getBy(userId, productId);

        if (reviewOpt.isEmpty()) {
            throw AppException.of404(AppExceptionType.REVIEW_GET_ERROR);
        } else return reviewOpt.get();
    }

    /**
     * [Fix] Review 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<Review> getAllBy(Long productId) {
        return reviewRepositoryQuery.getAllBy(productId);
    }

    /**
     * [Fix] Review 컬렉션을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<Review> getAllUserBy(Long userId) {
        return reviewRepositoryQuery.getAllUserBy(userId);
    }

    private Review createBy(User user, Product product, ReviewCreateDto reviewDto) {
        return Review.of(user, product, reviewDto.getReviewScore(), reviewDto.getReviewContent());
    }
}