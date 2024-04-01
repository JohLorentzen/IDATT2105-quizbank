package quizbank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void getAllQuizzes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/quiz"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getQuiz() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/quiz/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteQuiz() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/quiz/1"))
                .andExpect(status().isNoContent());
    }

}