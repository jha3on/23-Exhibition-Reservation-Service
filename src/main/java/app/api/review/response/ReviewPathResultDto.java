package app.api.review.response;

import app.core.rds.entity.review.Review;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class ReviewPathResultDto implements Serializable {
    private Long reviewId; // 리뷰 ID

    public static ReviewPathResultDto of(Review review) {
        return ReviewPathResultDto.builder()
                .reviewId(review.getId())
                .build();
    }
}