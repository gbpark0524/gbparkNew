package ke.pe.gbpark.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record GithubContributionVo(@JsonProperty("data") Data data) {

    public record Data(@JsonProperty("user") User user) {}

    public record User(@JsonProperty("contributionsCollection") ContributionsCollection contributionsCollection) {}

    public record ContributionsCollection(@JsonProperty("contributionCalendar") ContributionCalendar contributionCalendar) {}

    public record ContributionCalendar(
            @JsonProperty("totalContributions") int totalContributions,
            @JsonProperty("weeks") List<Week> weeks
    ) {}

    public record Week(@JsonProperty("contributionDays") List<ContributionDay> contributionDays) {}

    public record ContributionDay(
            @JsonProperty("color") String color,
            @JsonProperty("date") String date,
            @JsonProperty("contributionCount") int contributionCount,
            @JsonProperty("contributionLevel") String contributionLevel
    ) {}
}