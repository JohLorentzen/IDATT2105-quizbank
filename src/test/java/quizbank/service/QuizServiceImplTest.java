package quizbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import quizbank.dto.QuizDTO;
import quizbank.enums.Category;
import quizbank.enums.DifficultyLevel;
import quizbank.enums.Role;
import quizbank.model.Quiz;
import quizbank.model.User;
import quizbank.repository.QuizRepository;
import quizbank.repository.QuizUserRolesRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class QuizServiceImplTest {

    @InjectMocks
    private QuizServiceImpl quizService;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private UserService userService;

    @Mock
    private QuestionService questionService;

    @Mock
    private AuditLogService auditLogService;

    @Mock
    private QuizUserRolesRepository quizUserRolesRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getQuizzesCreatedByUserIdReturnsQuizzes() {
        Long userId = 1L;
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setCreatedByUser(new User());


        when(userService.findById(userId)).thenReturn(Optional.of(new User()));
        when(quizRepository.findByCreatedBy(any(User.class))).thenReturn(Arrays.asList(quiz));

        List<Quiz> quizzes = quizService.getQuizzesCreatedByUserId(userId);

        assertEquals(1, quizzes.size());
        assertEquals(quiz.getId(), quizzes.get(0).getId());
    }

    @Test
    public void getQuizzesCreatedByUserIdReturnsEmptyListWhenNoQuizzes() {
        Long userId = 1L;

        when(userService.findById(userId)).thenReturn(Optional.of(new User()));
        when(quizRepository.findByCreatedBy(any(User.class))).thenReturn(Collections.emptyList());

        List<Quiz> quizzes = quizService.getQuizzesCreatedByUserId(userId);

        assertEquals(0, quizzes.size());
    }

    @Test
    public void createOrUpdateQuizSavesQuiz() {
        QuizDTO quizDto = new QuizDTO();
        quizDto.setQuizId(1L);
        quizDto.setQuestions(Collections.emptyList());
        quizDto.setCategory(Category.GEOGRAPHY.name());
        quizDto.setDifficultyLevel(DifficultyLevel.EASY.name());
        quizDto.setCreatedByUserId(1L);
        when(userService.findById(1L)).thenReturn(Optional.of(new User()));

        quizService.createOrUpdateQuiz(quizDto);

        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    public void deleteQuizDeletesQuiz() {
        Long quizId = 1L;

        quizService.deleteQuiz(quizId);

        verify(quizRepository, times(1)).deleteById(quizId);
    }

    @Test
    public void getQuizByIdReturnsQuiz() {
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        quiz.setCreatedByUser(new User());
        quiz.setQuestions(Collections.emptyList());
        quiz.setCategory(Category.GEOGRAPHY);
        quiz.setDifficultyLevel(DifficultyLevel.EASY);

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));

        QuizDTO quizDto = quizService.getQuizById(quizId);

        assertEquals(quiz.getId(), quizDto.getQuizId());
    }

    @Test
    public void getQuizByIdReturnsNullWhenNoQuiz() {
        Long quizId = 1L;

        when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

        QuizDTO quizDto = quizService.getQuizById(quizId);

        assertNull(quizDto);
    }

    @Test
    public void shareQuizSavesQuiz() {
        Long quizId = 1L;
        Long userId = 1L;
        String role = "EDITOR";
        String sharedBy = "user";

        when(quizRepository.findById(quizId)).thenReturn(Optional.of(new Quiz()));
        when(userService.findById(userId)).thenReturn(Optional.of(new User()));

        quizService.shareQuiz(quizId, userId, role, sharedBy);

        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    public void getEditableQuizzesForUserReturnsQuizzes() {
        Long userId = 1L;
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setCreatedByUser(new User());
        quiz.setQuestions(Collections.emptyList());
        quiz.setCategory(Category.GEOGRAPHY);
        quiz.setDifficultyLevel(DifficultyLevel.EASY);
        Quiz quiz2 = new Quiz();
        quiz2.setId(2L);
        quiz2.setCreatedByUser(new User());
        quiz2.setQuestions(Collections.emptyList());
        quiz2.setCategory(Category.GENERAL_KNOWLEDGE);
        quiz2.setDifficultyLevel(DifficultyLevel.HARD);


        when(userService.findById(userId)).thenReturn(Optional.of(new User()));
        when(quizRepository.findByCreatedBy(any(User.class))).thenReturn(Arrays.asList(quiz));
        when(quizUserRolesRepository.findQuizzesByUserAndRole(any(User.class), any(Role.class))).thenReturn(Arrays.asList(quiz2));

        List<QuizDTO> quizzes = quizService.getEditableQuizzesForUser(userId);

        assertEquals(2, quizzes.size());
    }
}