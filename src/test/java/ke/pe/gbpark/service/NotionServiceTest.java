package ke.pe.gbpark.service;

import ke.pe.gbpark.response.NotionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NotionServiceTest {
    @Autowired
    NotionService notionService;

    @Test
    void getNewNotionList() {
        List<NotionResponse> newNotionList = notionService.getNewNotionList(11);
        System.out.println(newNotionList);
    }
}
