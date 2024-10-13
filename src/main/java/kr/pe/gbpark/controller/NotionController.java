package kr.pe.gbpark.controller;

import kr.pe.gbpark.response.NotionResponse;
import kr.pe.gbpark.response.Response;
import kr.pe.gbpark.service.NotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static kr.pe.gbpark.response.Response.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/portlet/notion")
@Slf4j
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
