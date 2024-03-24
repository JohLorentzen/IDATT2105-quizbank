package quizbank.dto;

import java.util.List;

public class QuizDTO {
    private int quizId;
    private String quizName;
    private int createdByUserId;

    private List<QuestionDTO> questions;

    public QuizDTO() {
    }

    public QuizDTO(int quizId, String quizName, int createByUserId, List<QuestionDTO> questions2) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.createdByUserId = createByUserId;
        this.questions = questions2;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId = createdByUserId;
    }


    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}