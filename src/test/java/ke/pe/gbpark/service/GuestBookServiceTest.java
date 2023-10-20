package ke.pe.gbpark.service;

import jakarta.transaction.Transactional;
import ke.pe.gbpark.request.GuestBookCreate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GuestBookServiceTest {
    @Autowired
    GuestBookService guestBookService;

    @Test
    void writeTest() {
        // given
        GuestBookCreate guestBookCreate = new GuestBookCreate();
        guestBookCreate.setTitle("title");
        guestBookCreate.setContent("content");
        guestBookCreate.setWriter("writer");
        guestBookCreate.setPassword("password");
        guestBookCreate.setEmail("email");
        guestBookCreate.setIp("ip");

        // when
        guestBookService.write(guestBookCreate);

        // then

    }

}