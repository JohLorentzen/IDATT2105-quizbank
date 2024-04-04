package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quizbank.dto.QuizAttemptDTO;
import quizbank.model.QuizAttempt;
import quizbank.repository.QuizAttemptRepository;
import quizbank.repository.QuizRepository;
import quizbank.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizAttemptService {

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    public void recordQuizAttempt(QuizAttemptDTO attemptDto) {
        QuizAttempt attempt = mapToEntity(attemptDto);
        quizAttemptRepository.save(attempt);
    }

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

    public List<QuizAttemptDTO> getUserQuizAttempts(Long userId) {
        List<QuizAttempt> attempts = quizAttemptRepository.findByUserId(userId);
        return attempts.stream()
                .map(this::mapToDto)
                .toList();
    }

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