package ke.pe.gbpark.service;

import jakarta.transaction.Transactional;
import ke.pe.gbpark.repository.GuestBookRepository;
import ke.pe.gbpark.request.GuestBookCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GuestBookServiceTest {
    @Autowired
    GuestBookService guestBookService;

    @Autowired
    GuestBookRepository guestBookRepository;

    @Test
    @DisplayName("GuestBookService write test")
    void writeTest() {
        // given
        GuestBookCreate guestBookCreate = GuestBookCreate.builder()
                .title("title")
                .writer("writer")
                .password("password")
                .content("content")
                .email("email")
                .ip("ip")
                .build();

        // when
        guestBookService.write(guestBookCreate);

        // then
        assertEquals(1L, guestBookRepository.count());
    }

}