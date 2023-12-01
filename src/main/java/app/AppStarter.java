package app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import java.util.Objects;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class AppStarter {
    private final CacheManager cacheManager;

    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class, args);
    }

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        cacheManager.getCacheNames().parallelStream().forEach(n -> Objects.requireNonNull(cacheManager.getCache(n)).clear());
    }
}