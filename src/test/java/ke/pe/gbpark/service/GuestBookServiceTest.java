package ke.pe.gbpark.service;

import jakarta.transaction.Transactional;
import ke.pe.gbpark.domain.GuestBook;
import ke.pe.gbpark.repository.GuestBookRepository;
import ke.pe.gbpark.request.GuestBookCreate;
import ke.pe.gbpark.response.GuestBookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GuestBookServiceTest {
    @Autowired
    GuestBookService guestBookService;

    @Autowired
    GuestBookRepository guestBookRepository;

    @BeforeEach
    void setUp() {
        guestBookRepository.deleteAll();
    }

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

    @Test
    @DisplayName("GuestBookService get one test")
    void get() {
        // given
        GuestBook guestBook = GuestBook.builder()
                .title("title")
                .content("con")
                .writer("me")
                .password("pass")
                .build();
        guestBookRepository.save(guestBook);

        // when
        Optional<GuestBookResponse> guestBookResponse = guestBookService.get(guestBook.getId());

        // then
        assertTrue(guestBookResponse.isPresent());
        GuestBookResponse response = guestBookResponse.get();
        assertNotNull(response);
        assertEquals(1L, guestBookRepository.count());
        assertEquals("title", response.getTitle());
        assertEquals("con", response.getContent());
        assertEquals("me", response.getWriter());
    }
}