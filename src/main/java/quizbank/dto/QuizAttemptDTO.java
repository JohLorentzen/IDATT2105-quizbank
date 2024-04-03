package quizbank.dto;

import java.time.LocalDateTime;

public class QuizAttemptDTO {
    private Long id;
    private Long quizId;
    private Long userId;
    private LocalDateTime attemptTime;
    private int totalQuestions;
    private int correctAnswers;

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
}
