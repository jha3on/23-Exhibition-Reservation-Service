package app.api.review.response;

import app.core.rds.entity.review.Review;
import app.share.app.utility.AppUtils;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @ToString @Builder
public class ReviewResultDto implements Serializable {
    private Long reviewId;
    private String reviewUserEmail;
    private String reviewContent;
    private Integer reviewScore;
    private LocalDateTime reviewCreatedAt;

    public static ReviewResultDto of(Review review) {
        var user = review.getUser();

        return ReviewResultDto.builder()
                .reviewId(review.getId())
                .reviewUserEmail(AppUtils.emailMasking(user.getEmail()))
                .reviewContent(review.getContent())
                .reviewScore(review.getScore())
                .reviewCreatedAt(review.getCreatedAt())
                .build();
    }

    public static List<ReviewResultDto> of(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewResultDto::of)
                .collect(Collectors.toList());
    }
}