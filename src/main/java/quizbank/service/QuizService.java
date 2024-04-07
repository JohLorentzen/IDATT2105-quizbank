package quizbank.service;

import quizbank.dto.QuizDTO;
import quizbank.model.Quiz;

import java.util.List;
import java.util.Set;

/**
 * Interface for the Quiz Service.
 * This service provides methods for managing quizzes.
 */
public interface QuizService {

    /**
     * Shares a quiz with a user.
     *
     * @param quizId the ID of the quiz to be shared
     * @param userId the ID of the user to share the quiz with
     * @param role the role of the user for the shared quiz
     * @param sharedBy the username of the user who shared the quiz
     */
    void shareQuiz(Long quizId, Long userId, String role, String sharedBy);

    /**
     * Converts a Quiz entity to a QuizDTO.
     *
     * @param quiz the Quiz entity
     * @return the converted QuizDTO
     */
    QuizDTO toDto(Quiz quiz);

    /**
     * Converts a QuizDTO to a Quiz entity.
     *
     * @param quizDTO the QuizDTO
     * @return the converted Quiz entity
     */
    Quiz toEntity(QuizDTO quizDTO);

    /**
     * Fetches all quizzes created by a specific user.
     *
     * @param userId the ID of the user
     * @return a list of quizzes created by the user
     */
    List<Quiz> getQuizzesCreatedByUserId(Long userId);

    /**
     * Fetches all quizzes.
     *
     * @return a list of all quizzes
     */
    List<QuizDTO> getAllQuizzes();

    /**
     * Creates or updates a quiz.
     *
     * @param quiz the quiz to be created or updated
     * @return the created or updated quiz
     */
    QuizDTO createOrUpdateQuiz(QuizDTO quiz);

    /**
     * Deletes a quiz.
     *
     * @param quizId the ID of the quiz to be deleted
     */
    void deleteQuiz(Long quizId);

    /**
     * Fetches a quiz by its ID.
     *
     * @param quizId the ID of the quiz
     * @return the quiz, or null if not found
     */
    QuizDTO getQuizById(Long quizId);

    /**
     * Fetches all quizzes that a user can edit.
     *
     * @param userId the ID of the user
     * @return a list of quizzes that the user can edit
     */
    List<QuizDTO> getEditableQuizzesForUser(Long userId);

    /**
     * Fetches all tags.
     *
     * @return a set of all tags
     */
    Set<String> getAllTags();
}