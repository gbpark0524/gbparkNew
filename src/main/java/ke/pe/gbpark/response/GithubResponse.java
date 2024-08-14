package ke.pe.gbpark.response;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
public class GithubResponse {
    private LocalDate date;
    private int commitCount;
    private String color;

    public GithubResponse(LocalDate date, int commitCount, String color) {
        this.date = date;
        this.commitCount = commitCount;
        this.color = color;
    }
}
