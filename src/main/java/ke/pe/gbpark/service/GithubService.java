package ke.pe.gbpark.service;

import ke.pe.gbpark.domain.GitHubContributionVo;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GithubService {
    private static final String GITHUB_API_URL = "https://api.github.com/graphql";
    private final WebClient webClient;

    public GithubService(Environment env) {
        String apiToken = env.getProperty("GITHUB_API_TOKEN");
        if (apiToken == null || apiToken.isEmpty()) {
            throw new IllegalStateException("GITHUB_API_TOKEN environment variable is not set");
        }
        this.webClient = WebClient.builder()
                .baseUrl(GITHUB_API_URL)
                .defaultHeader("Authorization", "Bearer " + apiToken)
                .build();
    }

    public Mono<GitHubContributionVo> getGithubContribution(String username, String fromDate, String toDate) {
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
                """, username, fromDate, toDate);

        return webClient.post()
                .bodyValue(new GraphQLRequestBody(query))
                .retrieve()
                .bodyToMono(GitHubContributionVo.class);
    }

    private record GraphQLRequestBody(String query) {
    }
}
