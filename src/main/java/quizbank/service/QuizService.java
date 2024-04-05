package quizbank.service;

import quizbank.dto.QuizDTO;
import quizbank.model.Quiz;

import java.util.List;
import java.util.Set;

public interface QuizService {
    void shareQuiz(Long quizId, Long userId, String role, String sharedBy);

    QuizDTO toDto(Quiz quiz);

    Quiz toEntity(QuizDTO quizDTO);

    List<Quiz> getQuizzesCreatedByUserId(Long userId);

    List<QuizDTO> getAllQuizzes();

    QuizDTO createOrUpdateQuiz(QuizDTO quiz);

    void deleteQuiz(Long quizId);

    QuizDTO getQuizById(Long quizId);

    List<QuizDTO> getEditableQuizzesForUser(Long userId);

    Set<String> getAllTags();
}