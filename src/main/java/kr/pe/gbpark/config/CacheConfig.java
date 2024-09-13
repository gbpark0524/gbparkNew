package kr.pe.gbpark.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.timer.Timer;

@Configuration
@EnableScheduling
@EnableCaching
@Slf4j
public class CacheConfig {

    @Scheduled(fixedRate = Timer.ONE_HOUR)
    @CacheEvict(
            value = {"githubContributions", "notionPages"},
            allEntries = true)
    public void clearEvents() {
        log.info("clear cache :clearEvents" );
    }
}
