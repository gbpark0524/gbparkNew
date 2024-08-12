package ke.pe.gbpark.service;

import ke.pe.gbpark.domain.GithubContributionVo;
import ke.pe.gbpark.response.GithubResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GithubServiceTest {
    @Autowired
    private GithubService githubService;

    @Test
    void getContributions() {
        Mono<List<GithubResponse>> contributions = githubService.getContributions();
        StepVerifier.create(contributions)
                .consumeNextWith(responses -> {
                    assertThat(responses).isNotEmpty();
                    assertThat(responses).allSatisfy(response -> {
                        assertThat(response.getDate()).isNotNull();
                        assertThat(response.getCommitCount()).isGreaterThanOrEqualTo(0);
                        assertThat(response.getColor()).isNotNull().isNotEmpty();
                    });
                    System.out.println(responses);
                })
                .verifyComplete();
    }
}