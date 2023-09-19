package ke.pe.gbpark.controller;

import ke.pe.gbpark.domain.GuestBook;
import ke.pe.gbpark.request.GuestBookCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GuestBookController {
    @PostMapping("/guestbook")
    public String post(@RequestBody GuestBookCreate request) {
        log.info("request : {}", request.toString());

        String title = request.getTitle();
        String content = request.getContent();
        String writer = request.getWriter();
        String password = request.getPassword();
        String email = request.getEmail();

        GuestBook guestBook = GuestBook.guestBookBuilder()
                .title(title)
                .content(content)
                .writer(writer)
                .password(password)
                .email(email)
                .ip("ip")
                .build();
        return guestBook.getTitle() + "/" + guestBook.getContent() + "/" + guestBook.getWriter() + "/" + guestBook.getPassword() + "/" + guestBook.getEmail() + "/" + guestBook.getIp();
    }
}
