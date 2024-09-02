package ke.pe.gbpark.response;

import ke.pe.gbpark.domain.NotionPageInfo;

public record NotionResponse(String id,
                             String url,
                             String title,
                             String iconType,
                             String iconContent) {
    public static NotionResponse from(NotionPageInfo pageInfo) {
        NotionPageInfo.Properties.Name name = pageInfo.properties().Name();
        // name 필드 없는 페이지 필터
        if (name == null) return null;
        String title = name.title().get(0).plain_text();
        String iconType = "";
        String iconContent = "";
        
        if (pageInfo.icon() != null) {
            iconType = pageInfo.icon().type().name();
            iconContent = switch (pageInfo.icon().type()) {
                case EMOJI -> pageInfo.icon().emoji();
                case EXTERNAL -> pageInfo.icon().external() != null ? pageInfo.icon().external().url() : null;
            };
        }

        return new NotionResponse(
                pageInfo.id(),
                pageInfo.url(),
                title,
                iconType,
                iconContent
        );
    }
}
