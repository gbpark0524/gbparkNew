package ke.pe.gbpark.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.timer.Timer;

@Configuration
@EnableScheduling
@EnableCaching
public class CacheConfig {
    final Logger logger = LoggerFactory.getLogger(CacheConfig.class.getName());

    @Scheduled(fixedRate = Timer.ONE_HOUR)
    @CacheEvict(
            value = {"githubContributions", "notionPages"},
            allEntries = true)
    public void clearEvents() {
        logger.debug("clear cache");
    }
}
