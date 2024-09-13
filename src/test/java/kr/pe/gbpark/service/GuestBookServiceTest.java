package kr.pe.gbpark.service;

import jakarta.transaction.Transactional;
import kr.pe.gbpark.domain.GuestBook;
import kr.pe.gbpark.repository.GuestBookRepository;
import kr.pe.gbpark.request.GuestBookCreate;
import kr.pe.gbpark.request.GuestBookSearch;
import kr.pe.gbpark.response.GuestBookResponse;
import kr.pe.gbpark.response.PaginationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
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
        // Given
        int totalGuestBooks = 20;
        int pageSize = 10;
        int pageNumber = 1;
        String[] titles = IntStream.range(0, totalGuestBooks)
                .mapToObj(i -> "foo" + i)
                .toArray(String[]::new);
        List<GuestBook> guestBooks = createGuestBooks(titles);
        guestBookRepository.saveAll(guestBooks);

        GuestBookSearch search = GuestBookSearch.builder()
                .page(pageNumber)
                .build();

        // When
        PaginationResponse<GuestBookResponse> response = guestBookService.getList(search);

        // Then
        assertThat(response.getSize()).isEqualTo(pageSize);
        assertThat(response.getItems().get(0).getTitle()).isEqualTo("foo19");
        assertThat(response.getTotalPage()).isEqualTo(2);
        assertThat(response.getTotalCount()).isEqualTo(totalGuestBooks);
        assertThat(response.getPage()).isEqualTo(pageNumber);
    }

    private List<GuestBook> createGuestBooks(String[] titles) {
        return IntStream.range(0, titles.length)
                .mapToObj(i -> GuestBook.builder(titles[i])
                        .content("bar" + i)
                        .build())
                .collect(Collectors.toList());
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