package quizbank.service;

import quizbank.dto.QuestionDTO;
import quizbank.model.Question;
import quizbank.model.Quiz;

import java.util.List;
import java.util.Set;

public interface QuestionService {

    List<QuestionDTO> toDto(List<Question> questions);

    List<Question> toEntity(List<QuestionDTO> questionDTOs, Quiz quiz);

    QuestionDTO toDto(Question question);

    Set<String> getAllTags();
}
