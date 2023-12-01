package app.core.rds.entity.product.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProductGroupType {

    /** 상품 하위 그룹 유형: 기획 */
    PRODUCTION("기획전"),

    /** 상품 하위 그룹 유형: 상설 */
    PERMANENT("상설전"),

    /** 상품 하위 그룹 유형: 특별전 */
    SPECIAL("특별전");

    @JsonValue @Getter
    public final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ProductGroupType of(String symbol) {
        return Arrays.stream(ProductGroupType.values())
                .filter(type -> type.name().equalsIgnoreCase(symbol))
                .findAny()
                .orElse(null);
    }
}