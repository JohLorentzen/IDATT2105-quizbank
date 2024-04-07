package quizbank.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import quizbank.enums.Category;
import quizbank.enums.DifficultyLevel;
import quizbank.enums.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a quiz.
 * Each quiz has a name, a category, a difficulty level, a creator, a list of questions, and a list of user roles.
 */
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private Set<QuizUserRoles> userRoles = new HashSet<>();
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizAttempt> attempts;


    public Quiz() {
    }

    public void addUserWithRole(User user, Role role) {
        QuizUserRoles quizUserRoles = new QuizUserRoles();
        quizUserRoles.setQuiz(this);
        quizUserRoles.setUser(user);
        quizUserRoles.setRole(role);
        this.userRoles.add(quizUserRoles);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedByUser(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Set<QuizUserRoles> getUserRoles() {
        return userRoles;
    }

}