package ke.pe.gbpark.service;

import ke.pe.gbpark.domain.GithubContributionVo;
import ke.pe.gbpark.domain.GithubContributionVo.ContributionCalendar;
import ke.pe.gbpark.response.GithubResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig
@Slf4j
public class GithubService {
    private static final String GITHUB_API_URL = "https://api.github.com/graphql";
    private final WebClient webClient;

    public GithubService(@Value("${external-api.github.token}") String apiToken) {
        if (apiToken == null || apiToken.isEmpty()) {
            throw new IllegalStateException("GITHUB_API_TOKEN environment variable is not set");
        }
        this.webClient = WebClient.builder()
                .baseUrl(GITHUB_API_URL)
                .defaultHeader("Authorization", "Bearer " + apiToken)
                .build();
    }

    @Cacheable(value = "githubContributions", key = "#root.method.name" )
    public Mono<List<GithubResponse>> getContributions() {
        log.debug("call getContributions");
        String fromDate = LocalDate.now(ZoneId.of("Asia/Seoul")).minusMonths(2).atStartOfDay(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_INSTANT);
        String toDate = LocalDate.now(ZoneId.of("Asia/Seoul")).atStartOfDay(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_INSTANT);
        String query = String.format("""
                query {
                    user(login: "%s") {
                        contributionsCollection(from: "%s", to: "%s") {
                            contributionCalendar {
                                totalContributions
                                weeks {
                                    contributionDays {
                                        color
                                        date
                                        contributionCount
                                        contributionLevel
                                    }
                                }
                            }
                        }
                    }
                }
                """, "gbpark0524", fromDate, toDate);

        Mono<GithubContributionVo> voMono = webClient.post()
                .bodyValue(new GraphQLRequestBody(query))
                .retrieve()
                .bodyToMono(GithubContributionVo.class)
                .cache();
        return voMono.map(vo -> {
            List<GithubResponse> responses = new ArrayList<>();
            ContributionCalendar calendar = vo.data().user().contributionsCollection().contributionCalendar();

            for (GithubContributionVo.Week week : calendar.weeks()) {
                for (GithubContributionVo.ContributionDay day : week.contributionDays()) {
                    LocalDate date = LocalDate.parse(day.date());
                    int commitCount = day.contributionCount();
                    String color = day.color();
                    responses.add(new GithubResponse(date, commitCount, color));
                }
            }

            return responses;
        });
    }

    private record GraphQLRequestBody(String query) {
    }
}
