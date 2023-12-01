package app.share.app.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;
import java.util.HashMap;

@Slf4j
@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    @Primary
    public CacheManager redisCacheManager(RedisConnectionFactory factory) {
        var redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofSeconds(RedisCacheKey.DEFAULT_TTL));

        var cacheConfigurations = new HashMap<String, RedisCacheConfiguration>();
        cacheConfigurations.put(RedisCacheKey.PRODUCT_LIST, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(RedisCacheKey.PRODUCT_LIST_TTL)));
        cacheConfigurations.put(RedisCacheKey.PROMOTION_LIST, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(RedisCacheKey.PROMOTION_LIST_TTL)));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(factory)
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}