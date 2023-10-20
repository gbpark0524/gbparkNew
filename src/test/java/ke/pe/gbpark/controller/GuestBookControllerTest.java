package ke.pe.gbpark.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class GuestBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/guestbook 등록 테스트")
    void postGuestBookTest() throws Exception {
        // given

        // expected
        mockMvc.perform(post("/guestbook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"postGuestBookTest\",\"content\":\"postGuestBookTestContent\",\"writer\":\"gbpark\",\"password\":\"pass\",\"email\":\"gbpark@mail.com\"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("/guestbook 등록 테스트")
    void postGuestBookValidTitleTest() throws Exception {
        // given

        // expected
        mockMvc.perform(post("/guestbook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"content\":\"postGuestBookTestContent\",\"writer\":\"gbpark\",\"password\":\"pass\",\"email\":\"gbpark@mail.com\"}"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}