package app.api.review.response;

import app.core.rds.entity.review.ReviewSum;
import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder
public class ReviewSumResultDto implements Serializable {
    private Integer reviewSumScore;
    private Integer reviewSumCount;

    public static ReviewSumResultDto of(ReviewSum reviewSum) {
        return ReviewSumResultDto.builder()
                .reviewSumScore(reviewSum.getScoreSum())
                .reviewSumCount(reviewSum.getScoreCount())
                .build();
    }
}