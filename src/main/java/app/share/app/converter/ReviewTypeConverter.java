package app.share.app.converter;

import app.core.rds.entity.review.type.ReviewStatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ReviewTypeConverter {
    public static class ReviewStatusTypeConverter implements Converter<String, ReviewStatusType> {
        public ReviewStatusType convert(String symbol) {
            return ReviewStatusType.of(symbol.toUpperCase());
        }
    }
}