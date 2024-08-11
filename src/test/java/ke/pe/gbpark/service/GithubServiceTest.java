package ke.pe.gbpark.service;

import ke.pe.gbpark.domain.GitHubContributionVo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class GithubServiceTest {
    @Autowired
    private GithubService githubService;

    @Test
    void getGithubContribution() {
        // Given
        String username = "gbpark0524";
        LocalDate fromDate = LocalDate.now().minusMonths(1);
        LocalDate toDate = LocalDate.now();

        String fromDateString = fromDate.atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        String toDateString = toDate.atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

        // When
        Mono<GitHubContributionVo> result = githubService.getGithubContribution(username, fromDateString, toDateString);

        // Then
        StepVerifier.create(result)
                .consumeNextWith(response -> {
                    System.out.println("API Response: " + response);

                    assertThat(response.data().user().contributionsCollection().contributionCalendar().totalContributions())
                            .as("Total contributions should be a non-negative integer")
                            .isGreaterThanOrEqualTo(0);

                    assertThat(response.data().user().contributionsCollection().contributionCalendar().weeks())
                            .as("Weeks should be a non-empty list")
                            .isNotEmpty();

                    GitHubContributionVo.ContributionDay firstDay = response.data().user().contributionsCollection()
                            .contributionCalendar().weeks().get(0).contributionDays().get(0);

                    assertThat(firstDay.color())
                            .as("Color should be a non-empty string")
                            .isNotBlank();

                    assertThat(firstDay.date())
                            .as("Date should be in the correct format")
                            .matches("\\d{4}-\\d{2}-\\d{2}");

                    assertThat(firstDay.contributionCount())
                            .as("Contribution count should be a non-negative integer")
                            .isGreaterThanOrEqualTo(0);
                })
                .verifyComplete();
    }
}