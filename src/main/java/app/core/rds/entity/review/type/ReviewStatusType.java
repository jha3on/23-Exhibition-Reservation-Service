package app.core.rds.entity.review.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

/**
 * 리뷰 상태 유형
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReviewStatusType {

    /** 리뷰 등록 */
    CREATED("리뷰 등록"),

    /** 리뷰 등록 취소 */
    CANCELED("리뷰 등록 취소");

    @JsonValue @Getter
    public final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ReviewStatusType of(String symbol) {
        return Arrays.stream(ReviewStatusType.values())
                .filter(type -> type.name().equalsIgnoreCase(symbol))
                .findAny()
                .orElse(null);
    }
}