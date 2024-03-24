package quizbank.service;

import quizbank.dto.QuizDTO;
import quizbank.model.Quiz;

public interface QuizService {
    QuizDTO toDto(Quiz quiz);
    Quiz toEntity(QuizDTO quizDTO);
    // Define other service operations, like create, update, find, delete, etc.
}