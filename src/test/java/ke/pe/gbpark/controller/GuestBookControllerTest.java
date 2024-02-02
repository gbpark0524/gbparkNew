package ke.pe.gbpark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ke.pe.gbpark.repository.GuestBookRepository;
import ke.pe.gbpark.request.GuestBookCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class GuestBookControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GuestBookRepository guestBookRepository;

    @BeforeEach
    void setUp() {
        guestBookRepository.deleteAll();
    }

    @Test
    @DisplayName("/guestbook insert test")
    void postGuestBookTest() throws Exception {
        // given
        GuestBookCreate guestBookCreate = GuestBookCreate.builder()
                .title("postGuestBookTest")
                .writer("gbpark")
                .password("pass")
                .content("postGuestBookTestContent")
                .email("gbpark@mail.com")
                .build();

        String json = objectMapper.writeValueAsString(guestBookCreate);

        // expected
        mockMvc.perform(post("/guestbook")
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
        GuestBookCreate guestBookCreate = GuestBookCreate.builder()
                .title("")
                .writer("gbpark")
                .password("pass")
                .content("postGuestBookTestContent")
                .email("gbpark@mail.com")
                .build();

        String json = objectMapper.writeValueAsString(guestBookCreate);

        // expected
        mockMvc.perform(post("/guestbook")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Invalid parameter"))
                .andExpect(jsonPath("$.target.title").value("title is mandatory"))
                .andDo(print());
    }
}