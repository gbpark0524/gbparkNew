package ke.pe.gbpark.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ke.pe.gbpark.domain.NotionPageInfo;
import ke.pe.gbpark.domain.NotionQuery;
import ke.pe.gbpark.domain.NotionQuery.Filter;
import ke.pe.gbpark.domain.NotionQuery.FilterValue;
import ke.pe.gbpark.domain.NotionQuery.Sort;
import ke.pe.gbpark.domain.NotionQuery.SortDirection;
import ke.pe.gbpark.response.NotionResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@CacheConfig
@Slf4j
public class NotionService {
    @Value("${external-api.notion.token}")
    private String NOTION_TOKEN;

    private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Cacheable(value = "notionPages", key = "#pageSize")
    public List<NotionResponse> getNewNotionList(int pageSize) {
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
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(query);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
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
                log.error(errorMessage);
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
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private static List<NotionResponse> parseResponse(String responseBody) throws IOException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode resultsNode = rootNode.path("results");
        List<NotionPageInfo> pageInfos = objectMapper.readValue(
                objectMapper.treeAsTokens(resultsNode),
                new TypeReference<>() {
                }
        );

        return pageInfos.stream()
                .map(NotionResponse::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}