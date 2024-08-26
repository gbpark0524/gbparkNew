package ke.pe.gbpark.service;

import jakarta.transaction.Transactional;
import ke.pe.gbpark.domain.NotionPageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotionServiceTest {
    @Autowired
    NotionService notionService;

    @Test
    void getNewNotionList() {
        List<NotionPageInfo> newNotionList = notionService.getNewNotionList(11);
        System.out.println(newNotionList);
    }
}
