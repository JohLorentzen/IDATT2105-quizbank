package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import quizbank.dto.QuizDTO;
import quizbank.enums.Category;
import quizbank.enums.DifficultyLevel;
import quizbank.enums.Role;
import quizbank.model.Quiz;
import org.springframework.stereotype.Service;
import quizbank.model.User;
import quizbank.repository.QuizRepository;
import quizbank.repository.QuizUserRolesRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;

    private final UserService userService;
    private final QuizRepository quizRepository;

    private AuditLogService auditLogService;

    private QuizUserRolesRepository quizUserRolesRepository;

    @Autowired
    public QuizServiceImpl(QuestionService questionService, UserService userService, QuizRepository quizRepository, AuditLogService auditLogService, QuizUserRolesRepository quizUserRolesRepository) {
        this.questionService = questionService;
        this.userService = userService;
        this.quizRepository = quizRepository;
        this.auditLogService = auditLogService;
        this.quizUserRolesRepository = quizUserRolesRepository;
    }

    @Override
    public List<Quiz> getQuizzesCreatedByUserId(Long userId) {
        return userService.findById(userId).map(quizRepository::findByCreatedBy).orElse(Collections.emptyList());
    }

    @Override
    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(this::toDto).toList();
    }

    @Override
    public QuizDTO createOrUpdateQuiz(QuizDTO quiz) {
        Quiz quizEntity = toEntity(quiz);
        quizRepository.save(quizEntity);
        return toDto(quizEntity);
    }

    @Override
    public void deleteQuiz(Long quizId) {
        quizRepository.deleteById(quizId);
    }

    @Override
    public QuizDTO getQuizById(Long quizId) {
        return quizRepository.findById(quizId).map(this::toDto).orElse(null);
    }

    @Override
    public void shareQuiz(Long quizId, Long userId, String role, String sharedBy) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        quiz.addUserWithRole(user, Role.valueOf(role));

        quizRepository.save(quiz);

        auditLogService.logAction(quizId, "Shared Quiz with User ID: " + userId + " as " + role, sharedBy);
    }

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
