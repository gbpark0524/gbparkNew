package kr.pe.gbpark.response;

import kr.pe.gbpark.domain.NotionPageInfo;
import kr.pe.gbpark.domain.NotionPageInfo.Properties.Name;
import kr.pe.gbpark.domain.NotionPageInfo.Properties.Name.TitleContent;
import kr.pe.gbpark.domain.NotionPageInfo.Properties.Summary;

import java.util.stream.Collectors;

public record NotionResponse(String id, String url, String title, String iconType, String iconContent) {
    public static NotionResponse from(NotionPageInfo pageInfo) {
        Name name = pageInfo.properties().Name();
        Summary summary = pageInfo.properties().Summary();
        if (name == null || name.title().isEmpty() || summary == null || summary.rich_text().isEmpty()) {
            return null;
        }

        String title = name.title().stream()
                .map(TitleContent::plain_text)
                .collect(Collectors.joining(" "));
        String iconType = "";
        String iconContent = "";

        if (pageInfo.icon() != null) {
            iconType = pageInfo.icon().type().name();
            iconContent = switch (pageInfo.icon().type()) {
                case EMOJI -> pageInfo.icon().emoji();
                case FILE -> pageInfo.icon().file().url();
                case EXTERNAL -> pageInfo.icon().external() != null ? pageInfo.icon().external().url() : null;
            };
        }

        return new NotionResponse(
                pageInfo.id(),
                pageInfo.public_url(),
                title,
                iconType,
                iconContent
        );
    }
}
