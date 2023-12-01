package app.api.review;

import app.api.review.response.ReviewSumResultDto;
import app.core.rds.service.product.ProductService;
import app.core.rds.service.review.ReviewService;
import app.core.rds.service.review.ReviewSumService;
import app.api.review.request.ReviewCreateDto;
import app.api.review.response.ReviewPathResultDto;
import app.api.review.response.ReviewResultDto;
import app.core.rds.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewApiService {
    private final UserService userService;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final ReviewSumService reviewSumService;

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * [Fix] 리뷰를 생성한다.
     */
    @Transactional
    public ReviewPathResultDto createReviewBy(Long userId, Long productId, ReviewCreateDto reviewDto) {
        var user = userService.getBy(userId);
        var product = productService.getBy(productId);
        var review = reviewService.saveBy(user, product, reviewDto);
        var reviewSum = reviewSumService.plusBy(review, product);

        return ReviewPathResultDto.of(review);
    }

    /**
     * 리뷰 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public ReviewSumResultDto getReviewSumBy(Long productId) {
        var product = productService.getBy(productId);
        var reviewSum = reviewSumService.getBy(product);

        return ReviewSumResultDto.of(reviewSum);
    }

    /**
     * 리뷰 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<ReviewResultDto> getReviewsBy(Long productId) {
        var product = productService.getBy(productId);
        var reviews = reviewService.getAllBy(productId);

        return ReviewResultDto.of(reviews);
    }

    /**
     * 리뷰 목록을 조회한다.
     */
    @Transactional(readOnly = true)
    public List<ReviewResultDto> getUserReviewsBy(Long userId) {
        var reviews = reviewService.getAllBy(userId);

        return ReviewResultDto.of(reviews);
    }

    // -----------------------------------------------------------------------------------------------------------------
}