package kr.pe.gbpark.service;

import jakarta.transaction.Transactional;
import kr.pe.gbpark.domain.GuestBook;
import kr.pe.gbpark.exception.InvalidPassword;
import kr.pe.gbpark.exception.NotFound;
import kr.pe.gbpark.repository.GuestBookRepository;
import kr.pe.gbpark.request.GuestBookCreate;
import kr.pe.gbpark.request.GuestBookSearch;
import kr.pe.gbpark.response.GuestBookResponse;
import kr.pe.gbpark.response.PaginationResponse;
import kr.pe.gbpark.util.security.EncryptionUtil;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class GuestBookServiceTest {
    @Autowired
    GuestBookService guestBookService;

    @Autowired
    GuestBookRepository guestBookRepository;

    @Autowired
    EncryptionUtil encryptionUtil;

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
        GuestBookCreate guestBookCreate = GuestBookCreate.builder(testTitle)
                .writer(testWriter)
                .password(testPass)
                .content(testContent)
                .email(testMail)
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


    @Test
    @DisplayName("deleteGuestBook: 게시물 삭제 성공")
    void deleteGuestbook() {
        // Given
        String correctPassword = "password123";
        GuestBook guestBook = GuestBook.builder("title")
                .writer("me")
                .password(encryptionUtil.encodePassword(correctPassword))
                .build();
        guestBookRepository.save(guestBook);
        Long id = guestBook.getId();

        //when
        guestBookService.deleteGuestbook(id, correctPassword);

        //then
        assertEquals(0L, guestBookRepository.count());
    }

    @Test
    @DisplayName("deleteGuestBook: 잘못된 비밀번호 삭제 예외")
    void deleteGuestbook_wrongPassword() {
        // Given
        String correctPassword = "password123";
        String wrongPassword = correctPassword + "1";
        GuestBook guestBook = GuestBook.builder("title")
                .writer("me")
                .password(encryptionUtil.encodePassword(correctPassword))
                .build();
        guestBookRepository.save(guestBook);
        Long id = guestBook.getId();

        //when then
        assertThrows(InvalidPassword.class, () -> guestBookService.deleteGuestbook(id, wrongPassword));
    }

    @Test
    @DisplayName("deleteGuestBook: 존재하지 않는 ID로 삭제 시도 시 예외")
    void deleteGuestbook_wrongId() {
        // Given
        String correctPassword = "password123";
        GuestBook guestBook = GuestBook.builder("title")
                .writer("me")
                .password(encryptionUtil.encodePassword(correctPassword))
                .build();
        guestBookRepository.save(guestBook);
        Long id = guestBook.getId();

        //when then
        assertThrows(NotFound.class, () -> guestBookService.deleteGuestbook(id + 1, correctPassword));
    }
}