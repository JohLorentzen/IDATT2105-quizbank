package quizbank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizAttemptsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void recordQuizAttempt() throws Exception {
        String json = "{ \"id\": 1, \"quizId\": 2, \"userId\": 3, \"attemptTime\": \"2023-10-10T10:10:10\", \"totalQuestions\": 10, \"correctAnswers\": 5 }";
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/quiz-attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getUserQuizAttempts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/quiz-attempts/1"))
                .andExpect(status().isOk());
    }

}