package app.core.rds.repository.review.query;

import app.core.rds.entity.review.Review;
import app.core.rds.entity.review.ReviewSum;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryQuery {

    /** [Fix] Review 객체 조회 */
    Optional<Review> getBy(Long userId, Long productId);

    /** [Fix] Review 컬렉션 조회 */
    List<Review> getAllBy(Long productId);

    /** [Fix] Review 컬렉션 조회 */
    List<Review> getAllUserBy(Long userId);

    /** [Fix] ReviewSum 객체 조회 */
    Optional<ReviewSum> getBy(Long productId);
}