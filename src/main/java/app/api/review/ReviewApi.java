package app.api.review;

import app.share.session.SessionKey;
import app.share.session.SessionUtils;
import app.api.review.request.ReviewCreateDto;
import app.share.app.payload.ApiResponse;
import app.share.app.payload.ApiResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewApi {
    private final ReviewApiService apiService;

    /** [Fix] 리뷰 목록 조회 */
    @GetMapping(value = {"/api/products/{productId}/reviews"})
    public ResponseEntity<?> getReviews(
        @PathVariable Long productId
    ) {
        var result = apiService.getReviewsBy(productId);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 리뷰 목록 조회 */
    @GetMapping(value = {"/api/reviews"})
    public ResponseEntity<?> getUserReviews() {
        var result = apiService.getUserReviewsBy(getCurrentUserBy());

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 리뷰 점수 조회 */
    @GetMapping(value = {"/api/products/{productId}/score"})
    public ResponseEntity<?> getReviewScore(
        @PathVariable Long productId
    ) {
        var result = apiService.getReviewSumBy(productId);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 리뷰 생성 */
    @PostMapping(value = {"/api/products/{productId}/reviews"})
    public ResponseEntity<?> createReview(
        @PathVariable Long productId,
        @RequestBody ReviewCreateDto reviewDto
    ) {
        var result = apiService.createReviewBy(getCurrentUserBy(), productId, reviewDto);

        return ApiResponse.of(HttpStatus.OK, ApiResponseType.API_OK, result);
    }

    /** [Fix] 로그인 세션 조회 */
    private Long getCurrentUserBy() {
        return (!hasCurrentUserBy()) ? null : SessionUtils.getAttributeBy(SessionKey.USER).getUserId();
    }

    /** [Fix] 로그인 세션 확인 */
    private Boolean hasCurrentUserBy() {
        return SessionUtils.hasAttributeBy(SessionKey.USER);
    }
}