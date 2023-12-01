package app.share.app.config;

import app.share.app.converter.PlaceTypeConverter;
import app.share.app.converter.ProductTypeConverter;
import app.share.app.converter.PromotionTypeConverter;
import app.share.app.converter.ReservationTypeConverter;
import app.share.app.converter.ReviewTypeConverter;
import app.share.app.converter.UserTypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserTypeConverter.UserSearchTypeConverter());
        registry.addConverter(new PlaceTypeConverter.PlaceGroupTypeConverter());
        registry.addConverter(new PlaceTypeConverter.PlaceGroupSubtypeConverter());
        registry.addConverter(new ProductTypeConverter.ProductGroupTypeConverter());
        registry.addConverter(new ProductTypeConverter.ProductGroupSubtypeConverter());
        registry.addConverter(new PromotionTypeConverter.PromotionGroupTypeConverter());
        registry.addConverter(new ReservationTypeConverter.ReservationStatusTypeConverter());
        registry.addConverter(new ReviewTypeConverter.ReviewStatusTypeConverter());
    }
}