package app.core.rds.entity.product.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProductGroupSubtype {

    /** 상품 등급 유형: 전체 관람가 */
    ALL("전체 관람가"),

    /** 상품 등급 유형: 12세 관람가 */
    AGE_12("12세 관람가"),

    /** 상품 등급 유형: 15세 관람가 */
    AGE_15("15세 관람가"),

    /** 상품 등급 유형: 18세 관람가 */
    AGE_18("18세 관람가");

    @JsonValue @Getter
    public final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ProductGroupSubtype of(String symbol) {
        return Arrays.stream(ProductGroupSubtype.values())
                .filter(type -> type.name().equalsIgnoreCase(symbol))
                .findAny()
                .orElse(null);
    }
}