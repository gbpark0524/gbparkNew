package kr.pe.gbpark.service;

import jakarta.transaction.Transactional;
import kr.pe.gbpark.domain.GuestBook;
import kr.pe.gbpark.exception.InvalidPassword;
import kr.pe.gbpark.exception.NotFound;
import kr.pe.gbpark.repository.GuestBookRepository;
import kr.pe.gbpark.request.GuestBookCreate;
import kr.pe.gbpark.request.GuestBookEdit;
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
        GuestBookCreate guestBookCreate = GuestBookCreate.builder(testTitle, testWriter, testPass)
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
    void deleteGuestBook() {
        // Given
        String correctPassword = "password123";
        GuestBook guestBook = GuestBook.builder("title")
                .writer("me")
                .password(encryptionUtil.encodePassword(correctPassword))
                .build();
        guestBookRepository.save(guestBook);
        Long id = guestBook.getId();

        //when
        guestBookService.deleteGuestBook(id, correctPassword);

        //then
        assertEquals(0L, guestBookRepository.count());
    }

    @Test
    @DisplayName("deleteGuestBook: 잘못된 비밀번호 삭제 예외")
    void deleteGuestBook_wrongPassword() {
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
        assertThrows(InvalidPassword.class, () -> guestBookService.deleteGuestBook(id, wrongPassword));
    }

    @Test
    @DisplayName("deleteGuestBook: 존재하지 않는 ID로 삭제 시도 시 예외")
    void deleteGuestBook_wrongId() {
        // Given
        String correctPassword = "password123";
        GuestBook guestBook = GuestBook.builder("title")
                .writer("me")
                .password(encryptionUtil.encodePassword(correctPassword))
                .build();
        guestBookRepository.save(guestBook);
        Long id = guestBook.getId();

        //when then
        assertThrows(NotFound.class, () -> guestBookService.deleteGuestBook(id + 1, correctPassword));
    }

    @DisplayName("방명록 수정")
    @Test
    void editGuestBook() {
        // given
        String pass = "pass";
        GuestBook guestBook = GuestBook.builder("기존 제목")
                .content("기존 내용")
                .writer("기존 작성자")
                .password(encryptionUtil.encodePassword(pass))
                .email("old@email.com")
                .build();

        guestBookRepository.save(guestBook);

        String title = "수정된 제목";
        String content = "수정된 내용";
        String writer = "수정된 작성자";
        String mail = "new@email.com";
        GuestBookEdit guestBookEdit = GuestBookEdit.builder()
                .title(title)
                .content(content)
                .password(pass)
                .writer(writer)
                .email(mail)
                .build();

        // when
        guestBookService.edit(guestBook.getId(), guestBookEdit);

        // then
        GuestBook changedGuestBook = guestBookRepository.findById(guestBook.getId())
                .orElseThrow(NotFound::new);

        assertThat(changedGuestBook.getTitle()).isEqualTo(title);
        assertThat(changedGuestBook.getContent()).isEqualTo(content);
        assertThat(changedGuestBook.getWriter()).isEqualTo(writer); // 수정되지 않은 필드는 유지
        assertThat(changedGuestBook.getEmail()).isEqualTo(mail); // 수정되지 않은 필드는 유지
    }

    @DisplayName("존재하지 않는 방명록 수정")
    @Test
    void editNotFoundGuestBook() {
        // given
        GuestBookEdit guestBookEdit = GuestBookEdit.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .password("pass")
                .build();

        // expected
        assertThrows(NotFound.class, () -> {
            guestBookService.edit(999L, guestBookEdit);
        });
    }

    @DisplayName("잘못된 비밀번호로 방명록 수정")
    @Test
    void editGuestBookWithWrongPassword() {
        // given
        GuestBook guestBook = GuestBook.builder("제목")
                .content("내용")
                .writer("작성자")
                .password(encryptionUtil.encodePassword("pass"))
                .build();

        guestBookRepository.save(guestBook);

        GuestBookEdit guestBookEdit = GuestBookEdit.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .password("wrongpass")
                .build();

        // expected
        assertThrows(InvalidPassword.class, () -> {
            guestBookService.edit(guestBook.getId(), guestBookEdit);
        });
    }
}