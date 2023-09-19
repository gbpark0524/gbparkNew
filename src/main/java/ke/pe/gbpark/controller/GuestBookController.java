package ke.pe.gbpark.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GuestBookController {
    @PostMapping("/guestbook")
    public String post() {
        return "hello world";
    }
}
