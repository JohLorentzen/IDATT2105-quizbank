package quizbank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import quizbank.dto.UserDTO;
import quizbank.service.UserService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuizAttemptsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    void recordQuizAttempt() throws Exception {
        String json = "{ \"id\": 1, \"quizId\": 2, \"userId\": 1, \"attemptTime\": \"2023-10-10T10:10:10\", \"totalQuestions\": 10, \"correctAnswers\": 5 }";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        when(userService.findUserByUsername(userDetails.getUsername())).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/quiz-attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getUserQuizAttempts() throws Exception {
        when(userService.findUserByUsername(anyString())).thenReturn(new UserDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/quiz-attempts"))
                .andExpect(status().isOk());
    }

}