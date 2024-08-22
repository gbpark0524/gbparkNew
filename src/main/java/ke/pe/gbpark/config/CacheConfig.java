package ke.pe.gbpark.config;

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
    @Scheduled(fixedRate = Timer.ONE_HOUR)
    @CacheEvict(
            value = {"githubContributions"},
            allEntries = true)
    public void clearEvents() {
        System.out.println("clear cache");
    }
}
