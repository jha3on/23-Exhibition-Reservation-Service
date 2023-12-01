package app.core.rds.entity.reservation.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;

/**
 * 예매 상태 유형
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReservationStatusType {

    /** 이용 예정 */
    READY("이용 예정"),

    /** 이용 완료 */
    COMPLETED("이용 완료"),

    /** 예매 취소 */
    CANCELED("예매 취소");

    @JsonValue @Getter
    public final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ReservationStatusType of(String symbol) {
        return Arrays.stream(ReservationStatusType.values())
                .filter(type -> type.name().equalsIgnoreCase(symbol))
                .findAny()
                .orElse(null);
    }

    public static List<ReservationStatusType> getAll() {
        return Arrays.asList(ReservationStatusType.values());
    }
}