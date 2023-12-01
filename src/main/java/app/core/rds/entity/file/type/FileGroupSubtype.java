package app.core.rds.entity.file.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum FileGroupSubtype {

    /** 장소 */
    PLACE(),

    /** 상품 */
    PRODUCT(),

    /** 프로모션 */
    PROMOTION();

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static FileGroupSubtype of(String symbol) {
        return FileGroupSubtype.valueOf(symbol.toUpperCase());
    }

    public static FileGroupSubtype from(String symbols) {
        var symbol = symbols.split("/")[1]; // place <- image/place

        return FileGroupSubtype.valueOf(symbol.toUpperCase());
    }
}