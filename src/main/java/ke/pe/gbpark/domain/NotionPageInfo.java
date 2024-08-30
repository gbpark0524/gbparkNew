package ke.pe.gbpark.domain;

import java.util.List;


public record NotionPageInfo(String id, String url, Icon icon, Properties properties) {

    public record Properties(Title title) {

        public record Title(List<TitleContent> title) {

            public record TitleContent(String plain_text) {
            }
        }
    }


    public record Icon(
            String type,
            String emoji,
            External external
    ) {

        public record External(String url) {
        }
    }
}
