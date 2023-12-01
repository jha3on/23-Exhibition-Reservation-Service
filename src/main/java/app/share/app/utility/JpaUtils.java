package app.share.app.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaUtils {
    public static <T> Boolean hasElements(List<T> collection) {
        return !CollectionUtils.isEmpty(collection);
    }

    public static <T> List<T> getOrEmpty(List<T> collection) {
        return !CollectionUtils.isEmpty(collection) ? collection : new ArrayList<>();
    }
}