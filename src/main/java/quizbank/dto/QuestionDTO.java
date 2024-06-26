package quizbank.dto;

import quizbank.enums.QuestionType;

import java.util.List;
import java.util.Set;

/**
 * Represents a question in a quiz.
 * This class is used to transfer question data between the frontend and the backend.
 */
public class QuestionDTO {
    private Long questionId;
    private String problem;
    private String solution;
    private QuestionType type;
    private List<String> choices;

    private Set<String> tags;
    private byte[] image;

    public QuestionDTO() {
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

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
