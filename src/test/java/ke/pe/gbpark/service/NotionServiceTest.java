package ke.pe.gbpark.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotionServiceTest {
    @Autowired
    NotionService notionService;

    @Test
    void getNewNotionList() {
        notionService.getNewNotionList();
    }
}
