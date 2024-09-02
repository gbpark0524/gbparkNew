package ke.pe.gbpark.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record NotionPageInfo(String id, String url, Icon icon, Properties properties) {

    public record Properties(Name Name) {
        public record Name(List<TitleContent> title) {
            public record TitleContent(String plain_text) {
            }
        }
    }


    public record Icon(
            IconType type,
            String emoji,
            External external
    ) {
        public enum IconType {
            @JsonProperty("emoji")
            EMOJI,
            @JsonProperty("external")
            EXTERNAL
        }

        public record External(String url) {
        }
    }
}
