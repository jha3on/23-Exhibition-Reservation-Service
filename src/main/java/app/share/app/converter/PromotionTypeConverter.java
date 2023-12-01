package app.share.app.converter;

import app.core.rds.entity.promotion.type.PromotionGroupType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class PromotionTypeConverter {
    public static class PromotionGroupTypeConverter implements Converter<String, PromotionGroupType> {
        public PromotionGroupType convert(String symbol) {
            return PromotionGroupType.of(symbol.toUpperCase());
        }
    }
}