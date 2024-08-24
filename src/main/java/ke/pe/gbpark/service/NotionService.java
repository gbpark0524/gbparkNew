package ke.pe.gbpark.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Objects;

@Service
public class NotionService {
    final Logger logger = LoggerFactory.getLogger(NotionService.class);

    @Value("#{environment['config.notion']}")
    private String AUTHORIZATION;

    public String getNewNotionList(int pageSize, String next) {
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
                .pageSize(10)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(query);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("wrong notion query : " + query);
        }
        RequestBody body = RequestBody.create(jsonString, mediaType);

        Request.Builder builder = new Request.Builder()
                .url("https://api.notion.com/v1/search")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", AUTHORIZATION)
                .addHeader("Notion-Version", notionVersion);

        try (Response response = client.newCall(builder.build()).execute()) {

            if (response.code() != HttpStatus.OK.value())
                throw new Exception("Code exception occur, code : " + response.code());

            return Objects.requireNonNull(response.body()).string();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }
}