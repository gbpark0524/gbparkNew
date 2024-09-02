package ke.pe.gbpark.controller;

import ke.pe.gbpark.domain.NotionPageInfo;
import ke.pe.gbpark.response.NotionResponse;
import ke.pe.gbpark.service.NotionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/portlet/notion")
public class NotionController {
    private final NotionService notionService;

    public NotionController(NotionService notionService) {
        this.notionService = notionService;
    }


    @GetMapping("/list/{size}")
    public List<NotionResponse> getNewNotionList(@PathVariable int size) {
        return notionService.getNewNotionList(size);
    }
}
