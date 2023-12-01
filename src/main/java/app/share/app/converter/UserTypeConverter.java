package app.share.app.converter;

import app.core.rds.entity.user.type.UserSearchType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class UserTypeConverter {
    public static class UserSearchTypeConverter implements Converter<String, UserSearchType> {
        public UserSearchType convert(String symbol) {
            return UserSearchType.of(symbol.toUpperCase());
        }
    }
}