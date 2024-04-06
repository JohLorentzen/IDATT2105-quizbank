package quizbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import quizbank.dto.QuizAttemptDTO;
import quizbank.model.QuizAttempt;
import quizbank.model.Quiz;
import quizbank.model.User;
import quizbank.repository.QuizAttemptRepository;
import quizbank.repository.QuizRepository;
import quizbank.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuizAttemptServiceTest {

    @InjectMocks
    private QuizAttemptService quizAttemptService;

    @Mock
    private QuizAttemptRepository quizAttemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuizRepository quizRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void recordQuizAttemptSavesAttempt() {
        QuizAttemptDTO attemptDto = new QuizAttemptDTO();
        attemptDto.setId(1L);
        attemptDto.setQuizId(1L);
        attemptDto.setUserId(1L);
        attemptDto.setAttemptTime(LocalDateTime.now());
        attemptDto.setTotalQuestions(10);
        attemptDto.setCorrectAnswers(5);

        quizAttemptService.recordQuizAttempt(attemptDto);

        verify(quizAttemptRepository, times(1)).save(any(QuizAttempt.class));
    }

    @Test
    public void getUserQuizAttemptsReturnsAttempts() {
        Long userId = 1L;
        QuizAttempt attempt = new QuizAttempt();
        attempt.setId(1L);
        attempt.setQuiz(new Quiz());
        attempt.setUser(new User());
        attempt.setAttemptTime(LocalDateTime.now());
        attempt.setTotalQuestions(10);
        attempt.setCorrectAnswers(5);

        when(quizAttemptRepository.findByUserId(userId)).thenReturn(Arrays.asList(attempt));

        List<QuizAttemptDTO> attempts = quizAttemptService.getUserQuizAttempts(userId);

        assertEquals(1, attempts.size());
        assertEquals(attempt.getId(), attempts.get(0).getId());
    }

    @Test
    public void getUserQuizAttemptsReturnsEmptyListWhenNoAttempts() {
        Long userId = 1L;

        when(quizAttemptRepository.findByUserId(userId)).thenReturn(Arrays.asList());

        List<QuizAttemptDTO> attempts = quizAttemptService.getUserQuizAttempts(userId);

        assertEquals(0, attempts.size());
    }
}