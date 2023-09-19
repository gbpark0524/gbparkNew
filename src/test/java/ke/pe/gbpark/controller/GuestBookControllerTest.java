package ke.pe.gbpark.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class GuestBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/guestbook 요청시 Hello World를 출력한다.")
    void test() throws Exception {
        // given

        // expected
        mockMvc.perform(post("/guestbook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"title\",\"content\":\"content\",\"writer\":\"writer\",\"password\":\"password\",\"email\":\"email\"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}