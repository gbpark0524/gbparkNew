package ke.pe.gbpark.service;

import jakarta.transaction.Transactional;
import ke.pe.gbpark.domain.GuestBook;
import ke.pe.gbpark.repository.GuestBookRepository;
import ke.pe.gbpark.request.GuestBookCreate;
import ke.pe.gbpark.request.GuestBookSearch;
import ke.pe.gbpark.response.GuestBookResponse;
import ke.pe.gbpark.response.PaginationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        final String testTitle = "Test Title";
        final String testWriter = "Test Writer";
        final String testPass = "testPass";
        final String testContent = "Test Content";
        final String testMail = "test@email.com";
        final String testIp = "127.0.0.1";
        GuestBookCreate guestBookCreate = GuestBookCreate.builder(testTitle)
                .writer(testWriter)
                .password(testPass)
                .content(testContent)
                .email(testMail)
                .ip(testIp)
                .build();

        // when
        guestBookService.write(guestBookCreate);

        // then
        List<GuestBook> savedGuestBooks = guestBookRepository.findAll();
        assertEquals(1, savedGuestBooks.size());

        GuestBook savedGuestBook = savedGuestBooks.get(0);
        assertEquals(testTitle, savedGuestBook.getTitle());
        assertEquals(testWriter, savedGuestBook.getWriter());
        assertEquals(testContent, savedGuestBook.getContent());
        assertEquals(testMail, savedGuestBook.getEmail());
        assertEquals(testIp, savedGuestBook.getIp());
        assertFalse(savedGuestBook.isSecret());
    }

    @Test
    @DisplayName("GuestBookService get one test")
    void get() {
        // given
        GuestBook guestBook = GuestBook.builder("title")
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

    @Test
    @DisplayName("GuestBookService get list test")
    void getList() {
        // given
        List<GuestBook> guestBooks = IntStream.range(0, 20)
                .mapToObj(i -> GuestBook.builder("foo" + i)
                        .content("bar1" + i)
                        .build())
                .collect(Collectors.toList());

        guestBookRepository.saveAll(guestBooks);

        GuestBookSearch guestBookSearch = GuestBookSearch.builder()
                .page(1)
                .build();

        // when
        PaginationResponse<GuestBookResponse> guestBookResponsePaginationResponse = guestBookService.getList(guestBookSearch);

        // then
        assertEquals(10L, guestBookResponsePaginationResponse.getItems().size());
        assertEquals("foo19", guestBookResponsePaginationResponse.getItems().get(0).getTitle());
    }

    @Test
    @DisplayName("GuestBookService get list with default test")
    void getListWithDefault() {
        // given
        List<GuestBook> guestBooks = IntStream.range(0, 20)
                .mapToObj(i -> GuestBook.builder("foo" + i)
                        .content("bar1" + i)
                        .build())
                .collect(Collectors.toList());

        guestBookRepository.saveAll(guestBooks);

        GuestBookSearch guestBookSearch = GuestBookSearch.builder().build();

        // when
        PaginationResponse<GuestBookResponse> guestBookResponsePaginationResponse = guestBookService.getList(guestBookSearch);

        // then
        assertEquals(guestBookSearch.getDefaultSize(), guestBookResponsePaginationResponse.getItems().size());
        assertEquals(guestBookSearch.getDefaultPage(), (int) guestBookResponsePaginationResponse.getPage());
    }

}