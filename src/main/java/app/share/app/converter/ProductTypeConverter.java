package app.share.app.converter;

import app.core.rds.entity.product.type.ProductGroupSubtype;
import app.core.rds.entity.product.type.ProductGroupType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ProductTypeConverter {
    public static class ProductGroupTypeConverter implements Converter<String, ProductGroupType> {
        public ProductGroupType convert(String symbol) {
            return ProductGroupType.of(symbol.toUpperCase());
        }
    }

    public static class ProductGroupSubtypeConverter implements Converter<String, ProductGroupSubtype> {
        public ProductGroupSubtype convert(String symbol) {
            return ProductGroupSubtype.of(symbol.toUpperCase());
        }
    }
}