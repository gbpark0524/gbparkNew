package kr.pe.gbpark.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class NotionQuery {
    private final Sort sort;
    private final Filter filter;
    @JsonProperty("page_size")
    private final int pageSize;

    @Getter
    @Builder
    @ToString
    public static class Sort {
        private final SortDirection direction;
        private final String timestamp = "last_edited_time";
    }

    @Getter
    @Builder
    @ToString
    public static class Filter {
        private final String property = "object";
        private final FilterValue value;
    }

    public enum SortDirection {
        @JsonProperty("ascending")
        ASC,
        @JsonProperty("descending")
        DESC
    }

    public enum FilterValue {
        @JsonProperty("database")
        DATABASE,
        @JsonProperty("page")
        PAGE
    }
}