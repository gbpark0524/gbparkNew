package ke.pe.gbpark.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotionParser {
    public static List<NotionPageInfo> parseResponse(String responseBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        JsonNode resultsNode = rootNode.path("results");

        List<NotionPageInfo> pageInfoList = new ArrayList<>();

        for (JsonNode pageNode : resultsNode) {
            String id = pageNode.path("id").asText();
            String url = pageNode.path("url").asText();

            JsonNode iconNode = pageNode.path("icon");
            String iconType = iconNode.path("type").asText();

            NotionPageInfo.Icon icon;
            if ("emoji".equals(iconType)) {
                String emoji = iconNode.path("emoji").asText();
                icon = new NotionPageInfo.Icon(iconType, emoji);
            } else {
                // For other types (e.g., "external"), we set emoji to null
                icon = new NotionPageInfo.Icon(iconType, null);
            }

            pageInfoList.add(new NotionPageInfo(id, url, icon));
        }

        return pageInfoList;
    }
}
