package quizbank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import quizbank.dto.QuizDTO;
import quizbank.dto.UserDTO;
import quizbank.service.QuizService;
import quizbank.service.UserService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private QuizService quizService;

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

    @Test
    @WithMockUser
    void createOrUpdateQuiz() throws Exception {
        when(userService.findUserByUsername(anyString())).thenReturn(new UserDTO());
        when(quizService.createOrUpdateQuiz(any())).thenReturn(new QuizDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\t\"quizId\": null,\n" +
                                "    \"quizName\": \"New Quiz\",\n" +
                                "    \"createdBy\": \"User\",\n" +
                                "    \"difficultyLevel\": \"HARD\",\n" +
                                "    \"category\": \"GENERAL_KNOWLEDGE\",\n" +
                                "    \"questions\": [\n" +
                                "        {\t\"questionId\": null,\n" +
                                "            \"problem\": \"New question?\",\n" +
                                "            \"solution\": \"New solution\"\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void getQuizzesCreatedByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/quiz/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getAllCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/quiz/categories"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getAllTags() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/quiz/tags"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getAuditLogForQuiz() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/quiz/1/audit-log"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getMyQuizzes() throws Exception {
        when(userService.findUserByUsername(anyString())).thenReturn(new UserDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/quiz/my-quizzes"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shareQuiz() throws Exception {
        when(userService.findUserByUsername(anyString())).thenReturn(new UserDTO());
        when(quizService.getQuizById(anyLong())).thenReturn(new QuizDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/quiz/1/share")
                        .param("userName", "testUser")
                        .param("role", "VIEWER"))
                .andExpect(status().isOk());
    }
}