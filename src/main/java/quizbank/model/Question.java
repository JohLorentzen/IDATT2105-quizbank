package quizbank.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import quizbank.enums.QuestionType;

import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Quiz quiz;

    private String problem;
    private String solution;

    @Enumerated(EnumType.STRING)
    private QuestionType type;
    @ElementCollection
    private List<String> choices; //For multiple-choice and fill-in-the-blanks questions


    public Question() {
    }

    public Question(Quiz quiz, String problem, String solution, QuestionType type, List<String> choices) {
        this.quiz = quiz;
        this.problem = problem;
        this.solution = solution;
        this.type = type;
        this.choices = choices;
    }

    public Question(Quiz quiz, String problem, String solution, QuestionType type) {
        this.quiz = quiz;
        this.problem = problem;
        this.solution = solution;
        this.type = type;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
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
}