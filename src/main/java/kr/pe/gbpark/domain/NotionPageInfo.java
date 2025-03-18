package kr.pe.gbpark.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record NotionPageInfo(String id, String url, String public_url, Icon icon, Properties properties) {

    public record Properties(Name Name, Summary Summary) {
        public record Name(List<TitleContent> title) {
            public record TitleContent(String plain_text) {
            }
        }

        public record Summary(List<RichText> rich_text) {
            public record RichText(String plain_text) {
            }
        }
    }


    public record Icon(
            IconType type,
            String emoji,
            File file,
            External external
    ) {
        public enum IconType {
            @JsonProperty("emoji")
            EMOJI,
            @JsonProperty("file")
            FILE,
            @JsonProperty("external")
            EXTERNAL
        }

        public record File(String url, String expiry_time) {
        }

        public record External(String url) {
        }
    }
}
