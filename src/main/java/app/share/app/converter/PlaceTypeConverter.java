package app.share.app.converter;

import app.core.rds.entity.place.type.PlaceGroupSubtype;
import app.core.rds.entity.place.type.PlaceGroupType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class PlaceTypeConverter {
    public static class PlaceGroupTypeConverter implements Converter<String, PlaceGroupType> {
        public PlaceGroupType convert(String symbol) {
            return PlaceGroupType.of(symbol.toUpperCase());
        }
    }

    public static class PlaceGroupSubtypeConverter implements Converter<String, PlaceGroupSubtype> {
        public PlaceGroupSubtype convert(String symbol) {
            return PlaceGroupSubtype.of(symbol.toUpperCase());
        }
    }
}