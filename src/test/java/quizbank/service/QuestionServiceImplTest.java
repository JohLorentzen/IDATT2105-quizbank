package quizbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import quizbank.dto.QuestionDTO;
import quizbank.model.Question;
import quizbank.model.Quiz;
import quizbank.repository.QuestionRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionRepository questionRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void toDtoConvertsQuestionToDto() {
        Question question = new Question();
        question.setId(1L);
        question.setProblem("problem");
        question.setSolution("solution");

        QuestionDTO dto = questionService.toDto(question);

        assertEquals(question.getId(), dto.getQuestionId());
        assertEquals(question.getProblem(), dto.getProblem());
        assertEquals(question.getSolution(), dto.getSolution());
    }

    @Test
    public void toEntityConvertsDtoToQuestion() {
        QuestionDTO dto = new QuestionDTO();
        dto.setQuestionId(1L);
        dto.setProblem("problem");
        dto.setSolution("solution");

        Quiz quiz = new Quiz();
        quiz.setId(1L);

        List<Question> question = questionService.toEntity(List.of(dto), quiz);

        assertEquals(1, question.size());
        assertEquals(dto.getQuestionId(), question.get(0).getId());
        assertEquals(dto.getProblem(), question.get(0).getProblem());
        assertEquals(dto.getSolution(), question.get(0).getSolution());
        assertEquals(quiz, question.get(0).getQuiz());
    }

    @Test
    public void getAllTagsReturnsAllTags() {
        Question question1 = new Question();
        question1.setTags(new HashSet<>(Arrays.asList("tag1", "tag2")));

        Question question2 = new Question();
        question2.setTags(new HashSet<>(Arrays.asList("tag3", "tag4")));

        when(questionRepository.findAll()).thenReturn(Arrays.asList(question1, question2));

        Set<String> tags = questionService.getAllTags();

        assertEquals(4, tags.size());
        assertEquals(new HashSet<>(Arrays.asList("tag1", "tag2", "tag3", "tag4")), tags);
    }
}