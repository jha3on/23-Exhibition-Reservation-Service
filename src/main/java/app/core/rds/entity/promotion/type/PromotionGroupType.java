package app.core.rds.entity.promotion.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PromotionGroupType {

    /**  */
    DAILY_BEST("오늘의 전시회"),

    /**  */
    WEEKLY_NEW("새로운 전시회"),

    /**  */
    WEEKLY_SPECIAL("특별한 전시회");

    @JsonValue @Getter
    public final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PromotionGroupType of(String symbol) {
        return Arrays.stream(PromotionGroupType.values())
                .filter(type -> type.name().equalsIgnoreCase(symbol))
                .findAny()
                .orElse(null);
    }
}