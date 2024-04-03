package quizbank.model;

import jakarta.persistence.*;
import quizbank.enums.QuestionType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "question_tags", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();
    @Lob
    private byte[] image;

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