package app.share.app.config.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"app.core.*.repository"})
// @EnableJpaRepositories(basePackages = {"app.core.*.repository"}, repositoryImplementationPostfix = "Logic")
public class JpaConfig {
    // ...
}