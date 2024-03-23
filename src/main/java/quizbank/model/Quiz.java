package quizbank.model;

import java.util.List;

public class Quiz {
    private int quizId;
    private String quizName;
    private int createByUserId;

    private List<Question> questions;

    public Quiz(int quizId, String quizName, int createByUserId, List<Question> questions2) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.createByUserId = createByUserId;
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

    public int getCreateByUserId() {
        return createByUserId;
    }

    public void setCreateByUserId(int createByUserId) {
        this.createByUserId = createByUserId;
    }


    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}