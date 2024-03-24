package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quizbank.dto.QuestionDTO;
import quizbank.model.Question;
import quizbank.model.Quiz;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Override
    public List<QuestionDTO> toDto(List<Question> questions) {
        return questions.stream().map(this::toDto).collect(Collectors.toList());
    }


    public List<Question> toEntity(List<QuestionDTO> questionDTOs, Quiz quiz) {
        return questionDTOs.stream().map(dto -> {
            Question question = new Question();
            question.setId(dto.getQuestionId());
            question.setProblem(dto.getProblem());
            question.setSolution(dto.getSolution());
            question.setQuiz(quiz);
            return question;
        }).collect(Collectors.toList());
    }


    @Override
    public QuestionDTO toDto(Question question) {
        return new QuestionDTO(question.getId(), question.getProblem(), question.getSolution());
    }

    @Override
    public Question toEntity(QuestionDTO questionDTO, int quizId) {
        return new Question();
    }
}
