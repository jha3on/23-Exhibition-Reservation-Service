package app.share.app.converter;

import app.core.rds.entity.reservation.type.ReservationStatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ReservationTypeConverter {
    public static class ReservationStatusTypeConverter implements Converter<String, ReservationStatusType> {
        public ReservationStatusType convert(String symbol) {
            return ReservationStatusType.of(symbol.toUpperCase());
        }
    }
}