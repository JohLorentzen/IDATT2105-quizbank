package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import quizbank.dto.QuizDTO;
import quizbank.model.Quiz;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;

    @Autowired
    public QuizServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public QuizDTO toDto(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setQuizId(quiz.getId());
        dto.setQuizName(quiz.getName());
        dto.setCreatedByUserId(quiz.getCreatedByUserId());
        dto.setQuestions(new QuestionServiceImpl().toDto(quiz.getQuestions()));
        return dto;
    }


    @Override
    public Quiz toEntity(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setId(quizDTO.getQuizId());
        quiz.setName(quizDTO.getQuizName());
        quiz.setCreatedByUserId(quizDTO.getCreatedByUserId());
        quiz.setQuestions(questionService.toEntity(quizDTO.getQuestions(), quiz));
        return quiz;
    }

}
