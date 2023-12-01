package app.api.review.request;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class ReviewCreateDto implements Serializable {
    private String reviewContent;
    private Integer reviewScore;
}