package ke.pe.gbpark.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Value("${notion.config.authorization}")
    private String AUTHORIZATION;

    public String getNewNotionList(int pageSize, String next) {
        final String notionVersion = "2021-05-13";
        final String index = "- log";

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        String jsonStr = "{\"query\":\"" + index + "\",\"sort\":{\"direction\":\"descending\",\"timestamp\":\"last_edited_time\"},\"filter\":{\"property\":\"object\",\"value\":\"page\"}";
        jsonStr += pageSize == -1 ? "" : ",\"page_size\":" + pageSize;
        jsonStr += next == null ? "" : ",\"start_cursor\":\"" + next + "\"";
        jsonStr += "}";
        RequestBody body = RequestBody.create(jsonStr, mediaType);
        ObjectMapper objectMapper = new ObjectMapper();

        Request.Builder builder = new Request.Builder()
                .url("https://api.notion.com/v1/search")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", AUTHORIZATION)
                .addHeader("Notion-Version", notionVersion);

        try (Response response = client.newCall(builder.build()).execute()){

            if(response.code() != HttpStatus.OK.value()) throw new Exception("Code exception occur, code : " + response.code());

            return Objects.requireNonNull(response.body()).string();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }
}