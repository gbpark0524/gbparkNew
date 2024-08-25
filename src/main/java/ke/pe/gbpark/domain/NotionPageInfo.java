package ke.pe.gbpark.domain;

public record NotionPageInfo(String id, String url, Icon icon) {
    public record Icon(String type, String emoji) {
    }
}
