package app.core.rds.entity.place.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PlaceGroupSubtype {

    /** 장소 그룹 유형: 서울 */
    SEOUL("서울"),

    /** 장소 그룹 유형: 경기 */
    GYEONGGI("경기"),

    /** 장소 그룹 유형: 인천 */
    INCHEON("인천");

    @JsonValue @Getter
    public final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PlaceGroupSubtype of(String symbol) {
        return Arrays.stream(PlaceGroupSubtype.values())
                .filter(type -> type.name().equalsIgnoreCase(symbol))
                .findAny()
                .orElse(null);
    }
}