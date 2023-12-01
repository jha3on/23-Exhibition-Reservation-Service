package app.core.rds.entity.place.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PlaceGroupType {

    /** 장소 그룹 유형: 미술관 */
    MUSEUM("미술관"),

    /** 장소 그룹 유형: 갤러리 */
    GALLERY("갤러리");

    @JsonValue @Getter
    public final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PlaceGroupType of(String symbol) {
        return Arrays.stream(PlaceGroupType.values())
                .filter(type -> type.name().equalsIgnoreCase(symbol))
                .findAny()
                .orElse(null);
    }
}