package app.share.app.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import java.time.Duration;

@Slf4j
@Configuration
@EnableRedisRepositories(
    basePackages = "app.sjh",
    enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP
)
public class RedisConfig {

    @Value(value = "${spring.data.redis.host}")
    private String redisHost;

    @Value(value = "${spring.data.redis.port}")
    private Integer redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        var redisConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        var redisClientConfiguration = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(1))
                .shutdownTimeout(Duration.ZERO)
                .build();

        return new LettuceConnectionFactory(redisConfiguration, redisClientConfiguration);
    }
}