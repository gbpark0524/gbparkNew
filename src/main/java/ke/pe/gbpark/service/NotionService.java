package ke.pe.gbpark.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ke.pe.gbpark.domain.NotionPageInfo;
import ke.pe.gbpark.domain.NotionQuery;
import ke.pe.gbpark.domain.NotionQuery.Filter;
import ke.pe.gbpark.domain.NotionQuery.FilterValue;
import ke.pe.gbpark.domain.NotionQuery.Sort;
import ke.pe.gbpark.domain.NotionQuery.SortDirection;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class NotionService {
    final Logger logger = LoggerFactory.getLogger(NotionService.class);

    @Value("#{environment['external-api.notion.token']}")
    private String NOTION_TOKEN;

    public List<NotionPageInfo> getNewNotionList(int pageSize) {
        final String notionVersion = "2022-06-28";

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        NotionQuery query = NotionQuery.builder()
                .sort(Sort.builder()
                        .direction(SortDirection.DESC)
                        .build())
                .filter(Filter.builder()
                        .value(FilterValue.PAGE)
                        .build())
                .pageSize(pageSize)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(query);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        RequestBody body = RequestBody.create(jsonString, mediaType);

        Request.Builder builder = new Request.Builder()
                .url("https://api.notion.com/v1/search")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + NOTION_TOKEN)
                .addHeader("Notion-Version", notionVersion);

        try (Response response = client.newCall(builder.build()).execute()) {

            ResponseBody responseBody = response.body();
            String responseBodyString = responseBody != null ? responseBody.string() : "No response body";

            if (!response.isSuccessful() || responseBody == null) {
                String errorMessage = String.format("Request failed with code: %d, body: %s", response.code(), responseBodyString);
                logger.error(errorMessage);
                throw new IOException(errorMessage);
            }
            return parseResponse(responseBodyString);
        } catch (Exception e) {
            /*
            TODO- error response
            {
                "object": "error",
                    "status": 401,
                    "code": "unauthorized",
                    "message": "API token is invalid.",
                    "developer_survey": "https://notionup.typeform.com/to/bllBsoI4?utm_source=postman",
                    "request_id": "e74e6789-df65-448a-aef0-09160fca540b"
            }*/
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private static List<NotionPageInfo> parseResponse(String responseBody) throws IOException {
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
                icon = new NotionPageInfo.Icon(iconType, null);
            }

            pageInfoList.add(new NotionPageInfo(id, url, icon));
        }

        return pageInfoList;
    }
}