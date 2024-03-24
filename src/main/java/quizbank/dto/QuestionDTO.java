package quizbank.dto;

public class QuestionDTO {
    private Long questionId;
    private String problem;
    private String solution;

    // Constructor, getters and setters
    public QuestionDTO() {
    }


    public QuestionDTO(Long questionId, String problem, String solution) {
        this.questionId = questionId;
        this.problem = problem;
        this.solution = solution;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
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