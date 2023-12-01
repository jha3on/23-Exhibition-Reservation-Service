package app.core.rds.entity.user.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

/**
 * 회원 검색 유형
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserSearchType {

    /** 이메일 */
    EMAIL("이메일"),

    /** 연락처 */
    CONTACT("연락처");

    @JsonValue @Getter
    public final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserSearchType of(String symbol) {
        return Arrays.stream(UserSearchType.values())
                .filter(type -> type.name().equalsIgnoreCase(symbol))
                .findAny()
                .orElse(null);
    }
}