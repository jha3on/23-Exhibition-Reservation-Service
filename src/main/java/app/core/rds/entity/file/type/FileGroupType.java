package app.core.rds.entity.file.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum FileGroupType {

    /** 이미지 */
    IMAGE();

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static FileGroupType of(String symbol) {
        return FileGroupType.valueOf(symbol.toUpperCase());
    }

    public static FileGroupType from(String symbols) {
        var symbol = symbols.split("/")[0]; // image <- image/place

        return FileGroupType.valueOf(symbol.toUpperCase());
    }
}