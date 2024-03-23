package quizbank.model;

public class Question {
    private int questionId;
    private String problem;
    private String solution;

    // Constructor, getters and setters
    public Question() {
    }


    public Question(int questionId, String problem, String solution) {
        this.questionId = questionId;
        this.problem = problem;
        this.solution = solution;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}