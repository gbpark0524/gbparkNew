package ke.pe.gbpark.controller;

import ke.pe.gbpark.response.NotionResponse;
import ke.pe.gbpark.response.Response;
import ke.pe.gbpark.service.NotionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ke.pe.gbpark.response.Response.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/portlet/notion")
public class NotionController {
    private final NotionService notionService;

    public NotionController(NotionService notionService) {
        this.notionService = notionService;
    }


    @GetMapping("/list/{size}")
    public  Response<List<NotionResponse>> getNewNotionList(@PathVariable int size) {
        List<NotionResponse> notionList = notionService.getNewNotionList(size);
        return new Response<>(true, SUCCESS_MESSAGE, notionList);
    }
}
