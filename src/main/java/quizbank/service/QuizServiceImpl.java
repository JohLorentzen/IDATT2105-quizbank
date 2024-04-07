package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quizbank.dto.QuizDTO;
import quizbank.enums.Category;
import quizbank.enums.DifficultyLevel;
import quizbank.enums.Role;
import quizbank.model.Quiz;
import quizbank.model.User;
import quizbank.repository.QuizRepository;
import quizbank.repository.QuizUserRolesRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing quizzes.
 */
@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private QuizUserRolesRepository quizUserRolesRepository;

    /**
     * Fetches all quizzes created by a specific user.
     *
     * @param userId the ID of the user
     * @return a list of quizzes created by the user
     */
    @Override
    public List<Quiz> getQuizzesCreatedByUserId(Long userId) {
        return userService.findById(userId).map(quizRepository::findByCreatedBy).orElse(Collections.emptyList());
    }

    /**
     * Fetches all quizzes.
     *
     * @return a list of all quizzes
     */
    @Override
    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(this::toDto).toList();
    }

    /**
     * Creates or updates a quiz.
     *
     * @param quiz the quiz to be created or updated
     * @return the created or updated quiz
     */
    @Override
    public QuizDTO createOrUpdateQuiz(QuizDTO quiz) {
        Quiz quizEntity = toEntity(quiz);
        quizRepository.save(quizEntity);
        return toDto(quizEntity);
    }

    /**
     * Deletes a quiz.
     *
     * @param quizId the ID of the quiz to be deleted
     */
    @Override
    public void deleteQuiz(Long quizId) {
        quizRepository.deleteById(quizId);
    }

    /**
     * Fetches a quiz by its ID.
     *
     * @param quizId the ID of the quiz
     * @return the quiz, or null if not found
     */
    @Override
    public QuizDTO getQuizById(Long quizId) {
        return quizRepository.findById(quizId).map(this::toDto).orElse(null);
    }

    /**
     * Shares a quiz with a user.
     *
     * @param quizId the ID of the quiz to be shared
     * @param userId the ID of the user to share the quiz with
     * @param role the role of the user for the shared quiz
     * @param sharedBy the username of the user who shared the quiz
     */
    @Override
    public void shareQuiz(Long quizId, Long userId, String role, String sharedBy) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        quiz.addUserWithRole(user, Role.valueOf(role));

        quizRepository.save(quiz);

        auditLogService.logAction(quizId, "Shared quiz with " + user.getUsername() + " as " + role, sharedBy);
    }

    /**
     * Fetches all quizzes that a user can edit.
     *
     * @param userId the ID of the user
     * @return a list of quizzes that the user can edit
     */
    @Override
    public List<QuizDTO> getEditableQuizzesForUser(Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Quiz> createdQuizzes = quizRepository.findByCreatedBy(user);

        List<Quiz> sharedQuizzes = quizUserRolesRepository.findQuizzesByUserAndRole(user, Role.EDITOR);

        Set<Quiz> combinedQuizzes = new HashSet<>(createdQuizzes);
        combinedQuizzes.addAll(sharedQuizzes);

        return combinedQuizzes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Fetches all tags.
     *
     * @return a set of all tags
     */
    @Override
    public Set<String> getAllTags() {
        return questionService.getAllTags();
    }

    /**
     * Converts a Quiz entity to a QuizDTO.
     *
     * @param quiz the Quiz entity
     * @return the converted QuizDTO
     */
    @Override
    public QuizDTO toDto(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setQuizId(quiz.getId());
        dto.setQuizName(quiz.getName());
        dto.setCreatedByUserId(quiz.getCreatedBy().getId());
        dto.setQuestions(new QuestionServiceImpl().toDto(quiz.getQuestions()));
        dto.setCategory(quiz.getCategory().toString());
        dto.setDifficultyLevel(quiz.getDifficultyLevel().toString());
        return dto;
    }

    /**
     * Converts a QuizDTO to a Quiz entity.
     *
     * @param quizDTO the QuizDTO
     * @return the converted Quiz entity
     */
    @Override
    public Quiz toEntity(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setId(quizDTO.getQuizId());
        quiz.setName(quizDTO.getQuizName());
        quiz.setCreatedByUser(userService.findById(quizDTO.getCreatedByUserId()).orElse(null));
        quiz.setQuestions(questionService.toEntity(quizDTO.getQuestions(), quiz));
        quiz.setCategory(Category.valueOf(quizDTO.getCategory()));
        quiz.setDifficultyLevel(DifficultyLevel.valueOf(quizDTO.getDifficultyLevel()));
        return quiz;
    }

}