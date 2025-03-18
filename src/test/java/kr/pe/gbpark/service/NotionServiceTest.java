package kr.pe.gbpark.service;

import kr.pe.gbpark.response.NotionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NotionServiceTest {
    @Autowired
    NotionService notionService;

    @Test
    void getNewNotionList() {
        List<NotionResponse> newNotionList = notionService.getNewNotionList(11);

        Assertions.assertFalse(newNotionList.isEmpty());
        System.out.println("size: " + newNotionList.size());
        System.out.println("newNotionList: " + newNotionList);
    }
}
