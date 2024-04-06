package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quizbank.dto.QuizAttemptDTO;
import quizbank.model.QuizAttempt;
import quizbank.repository.QuizAttemptRepository;
import quizbank.repository.QuizRepository;
import quizbank.repository.UserRepository;

import java.util.List;

/**
 * Service class for managing quiz attempts.
 */
@Service
public class QuizAttemptService {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    /**
     * Records a quiz attempt.
     *
     * @param attemptDto the data transfer object representing the quiz attempt
     */
    public void recordQuizAttempt(QuizAttemptDTO attemptDto) {
        QuizAttempt attempt = mapToEntity(attemptDto);
        quizAttemptRepository.save(attempt);
    }

    /**
     * Maps a QuizAttemptDTO to a QuizAttempt entity.
     *
     * @param attemptDto the data transfer object representing the quiz attempt
     * @return the QuizAttempt entity
     */
    private QuizAttempt mapToEntity(QuizAttemptDTO attemptDto) {
        QuizAttempt attempt = new QuizAttempt();
        attempt.setId(attemptDto.getId());
        attempt.setQuiz(quizRepository.findById(attemptDto.getQuizId()).orElse(null));
        attempt.setUser(userRepository.findById(attemptDto.getUserId()).orElse(null));
        attempt.setAttemptTime(attemptDto.getAttemptTime());
        attempt.setTotalQuestions(attemptDto.getTotalQuestions());
        attempt.setCorrectAnswers(attemptDto.getCorrectAnswers());
        return attempt;
    }

    /**
     * Fetches all quiz attempts of a user.
     *
     * @param userId the ID of the user
     * @return a list of data transfer objects representing the quiz attempts
     */
    public List<QuizAttemptDTO> getUserQuizAttempts(Long userId) {
        List<QuizAttempt> attempts = quizAttemptRepository.findByUserId(userId);
        return attempts.stream()
                .map(this::mapToDto)
                .toList();
    }

    /**
     * Maps a QuizAttempt entity to a QuizAttemptDTO.
     *
     * @param quizAttempt the QuizAttempt entity
     * @return the data transfer object representing the quiz attempt
     */
    private QuizAttemptDTO mapToDto(QuizAttempt quizAttempt) {
        QuizAttemptDTO dto = new QuizAttemptDTO();
        dto.setId(quizAttempt.getId());
        dto.setQuizId(quizAttempt.getQuiz().getId());
        dto.setQuizName(quizAttempt.getQuiz().getName());
        dto.setQuizDifficulty(quizAttempt.getQuiz().getDifficultyLevel());
        dto.setQuizCategory(quizAttempt.getQuiz().getCategory());
        dto.setUserId(quizAttempt.getUser().getId());
        dto.setAttemptTime(quizAttempt.getAttemptTime());
        dto.setTotalQuestions(quizAttempt.getTotalQuestions());
        dto.setCorrectAnswers(quizAttempt.getCorrectAnswers());
        return dto;
    }
}