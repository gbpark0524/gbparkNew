package kr.pe.gbpark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kr.pe.gbpark.domain.GuestBook;
import kr.pe.gbpark.repository.GuestBookRepository;
import kr.pe.gbpark.request.GuestBookCreate;
import kr.pe.gbpark.response.GuestBookResponse;
import kr.pe.gbpark.response.Response;
import kr.pe.gbpark.util.security.EncryptionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class GuestBookControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GuestBookRepository guestBookRepository;
    
    @Autowired
    private EncryptionUtil encryptionUtil;

    @BeforeEach
    void setUp() {
        guestBookRepository.deleteAll();
        System.out.println("Data deleted, count: " + guestBookRepository.count());
    }

    @Test
    @DisplayName("/guestbook insert test")
    void postGuestBook() throws Exception {
        // given
        GuestBookCreate guestBookCreate = GuestBookCreate.builder("postGuestBookTest", "gbpark", "pass")
                .content("postGuestBookTestContent")
                .email("gbpark@mail.com")
                .build();

        String json = objectMapper.writeValueAsString(guestBookCreate);

        // expected
        mockMvc.perform(post("/board/guestbook")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/guestbook validation test")
    void postGuestBookValidTitleTest() throws Exception {
        // given
        GuestBookCreate guestBookCreate = GuestBookCreate.builder("", "gbpark", "pass")
                .content("postGuestBookTestContent")
                .email("gbpark@mail.com")
                .build();

        String json = objectMapper.writeValueAsString(guestBookCreate);

        // expected
        mockMvc.perform(post("/board/guestbook")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Invalid parameter"))
                .andExpect(jsonPath("$.target.title").value("title is mandatory"))
                .andDo(print());
    }

    @Test
    void getGuestBook() throws Exception {
        // given
        GuestBook guestBook = GuestBook.builder("postGuestBookTest")
                .content("Test content")
                .writer("Test writer")
                .password("pass")
                .build();
        guestBookRepository.save(guestBook);

        GuestBookResponse bookResponse = new GuestBookResponse(guestBook);
        Response<GuestBookResponse> response = new Response<>(true, Response.SUCCESS_MESSAGE, bookResponse);
        String expectedJson = objectMapper.writeValueAsString(response);

        // expected
        mockMvc.perform(get("/board/guestbook/" + guestBook.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(Response.SUCCESS_MESSAGE))
                .andExpect(content().json(expectedJson, false))
                .andDo(print());
    }

    @Test
    void getPaginationGuestBook() throws Exception {
        // given
        List<GuestBook> guestBooks = IntStream.range(0, 20)
                .mapToObj(i -> GuestBook.builder("foo" + i)
                        .content("bar" + i)
                        .build())
                .toList();

        guestBookRepository.saveAll(guestBooks);

        // expected
        mockMvc.perform(get("/board/guestbook?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()", is(10)))
                .andExpect(jsonPath("$.items[0].title").value("foo19"))
                .andExpect(jsonPath("$.items[0].content").value("bar19"))
                .andDo(print());
    }

    @Test
    @DisplayName("방명록 삭제 - 정상 케이스")
    void deleteGuestBookTest() throws Exception {
        // given
        String password = "testPassword";
        GuestBook guestBook = GuestBook.builder("삭제 테스트")
                .content("삭제될 내용")
                .writer("작성자")
                .password(encryptionUtil.encodePassword(password))
                .build();
        guestBookRepository.save(guestBook);

        // expected
        mockMvc.perform(delete("/board/guestbook/" + guestBook.getId())
                        .header("Board-Password", password)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("방명록 삭제 - 잘못된 비밀번호")
    void deleteGuestBookInvalidPassword() throws Exception {
        // given
        String password = "testPassword";
        String wrongPassword = "wrongPassword";
        GuestBook guestBook = GuestBook.builder("삭제 테스트")
                .content("삭제될 내용")
                .writer("작성자")
                .password(encryptionUtil.encodePassword(password))
                .build();
        guestBookRepository.save(guestBook);

        // expected
        mockMvc.perform(delete("/board/guestbook/" + guestBook.getId())
                        .header("Board-Password", wrongPassword)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("wrong password"))
                .andDo(print());
    }

    @Test
    @DisplayName("방명록 삭제 - 존재하지 않는 게시글")
    void deleteGuestBookNotFound() throws Exception {
        // given
        Long nonExistentId = 999L;

        // expected
        mockMvc.perform(delete("/board/guestbook/" + nonExistentId)
                        .header("Board-Password", "anyPassword")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("대상이 존재하지 않습니다"))
                .andDo(print());
    }

    @Test
    @DisplayName("방명록 삭제 - 비밀번호 누락")
    void deleteGuestBookMissingPassword() throws Exception {
        // given
        GuestBook guestBook = GuestBook.builder("삭제 테스트")
                .content("삭제될 내용")
                .writer("작성자")
                .password("password")
                .build();
        guestBookRepository.save(guestBook);

        // expected
        mockMvc.perform(delete("/board/guestbook/" + guestBook.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }
}