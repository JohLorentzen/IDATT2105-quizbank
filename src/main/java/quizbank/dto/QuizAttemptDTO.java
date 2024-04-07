package quizbank.dto;

import quizbank.enums.Category;
import quizbank.enums.DifficultyLevel;

import java.time.LocalDateTime;

/**
 * Represents a quiz attempt by a user.
 * This class is used to transfer quiz attempt data between the frontend and the backend.
 */
public class QuizAttemptDTO {
    private Long id;
    private Long quizId;
    private String quizName;
    private Long userId;
    private LocalDateTime attemptTime;
    private int totalQuestions;
    private int correctAnswers;
    private DifficultyLevel quizDifficulty;
    private Category quizCategory;

    public QuizAttemptDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(LocalDateTime attemptTime) {
        this.attemptTime = attemptTime;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }


    public double getScore() {
        if (totalQuestions > 0) {
            return ((double) correctAnswers / totalQuestions) * 100;
        }
        return 0;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizDifficulty(DifficultyLevel quizDifficulty) {
        this.quizDifficulty = quizDifficulty;
    }

    public DifficultyLevel getQuizDifficulty() {
        return quizDifficulty;
    }

    public void setQuizCategory(Category quizCategory) {
        this.quizCategory = quizCategory;
    }

    public Category getQuizCategory() {
        return quizCategory;
    }
}
