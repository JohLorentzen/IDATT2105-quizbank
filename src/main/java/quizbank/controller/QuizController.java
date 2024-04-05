package quizbank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import quizbank.dto.QuizDTO;
import quizbank.dto.UserDTO;
import quizbank.enums.Category;
import quizbank.enums.Role;
import quizbank.model.AuditLogEntry;
import quizbank.model.Quiz;
import quizbank.service.AuditLogService;
import quizbank.service.QuizService;
import quizbank.service.UserService;

import java.util.List;
import java.util.stream.Stream;

@Tag(
        name = "Quiz Controller",
        description = "Retrieve, create, update and delete quizzes. Get available quiz categories."
)
@RestController
@RequestMapping("/rest")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;
    @Autowired
    private AuditLogService auditLogService;

    @Operation(
            summary = "Get all quizzes",
            description = "Provides a list of all quizDTO objects"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all quizzes")
    @GetMapping(path = "/quiz", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<QuizDTO> quizDTOs = quizService.getAllQuizzes();
        return new ResponseEntity<>(quizDTOs, HttpStatus.OK);
    }

    @Operation(
        summary = "Creates or updates a quiz",
        description = "Creates or updates a quizDTO object from the provided payload"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Updated quiz successfully"),
            @ApiResponse(responseCode = "201", description = "Created a new quiz successfully")
    })
    @PostMapping("/quiz")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrUpdateQuiz(Authentication authentication, @RequestBody QuizDTO quiz) {
        if (quiz.getCreatedByUserId() == null) {
            quiz.setCreatedByUserId(userService.findUserByUsername(authentication.getName()).getId());
        }
        QuizDTO savedQuiz = quizService.createOrUpdateQuiz(quiz);
        if (quiz.getQuizId() != null) {
            auditLogService.logAction(savedQuiz.getQuizId(), "Quiz updated", authentication.getName());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            auditLogService.logAction(savedQuiz.getQuizId(), "Quiz created", authentication.getName());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @Operation(
        summary = "Deletes a quiz by its id",
        description = "Deletes a quiz based on its id"
    )
    @ApiResponse(responseCode = "204", description = "Deleted the quiz successfully")
    @DeleteMapping("/quiz/{quizId}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long quizId, Authentication authentication) {
        quizService.deleteQuiz(quizId);
        auditLogService.logAction(quizId, "Quiz deleted", authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
        summary = "Get quiz by id",
        description = "Provides the quizDTO object with the given quiz id"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved quiz by given id")
    @GetMapping(path = "/quiz/{quizId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QuizDTO getQuiz(@PathVariable("quizId") Long quizId) {
        return quizService.getQuizById(quizId);
    }

    @Operation(
        summary = "Retrieve all quizzes created by a user",
        description = "Provides a list of all quizDTO objects that belongs the user with that user id"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved quizzes that belongs to user")
    @GetMapping(path = "/quiz/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuizDTO>> getQuizzesCreatedByUser(@PathVariable("userId") Long userId) {
        List<Quiz> quizzesCreatedByUserId = quizService.getQuizzesCreatedByUserId(userId);
        List<QuizDTO> list = quizzesCreatedByUserId.stream().map(quiz -> quizService.toDto(quiz)).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Operation(
        summary = "Retrieves all quiz categories",
        description = "Provides a list of all quiz categories"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories")
    @GetMapping("/quiz/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = Stream.of(Category.values()).map(Category::name).toList();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(
        summary = "Get audit log for a quiz",
        description = "Provides a list of all audit log entries for a quiz with the given quiz id"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved audit log for quiz")
    @GetMapping("/quiz/{quizId}/audit-log")
    public ResponseEntity<List<AuditLogEntry>> getAuditLogForQuiz(@PathVariable Long quizId) {
        List<AuditLogEntry> auditLogs = auditLogService.findByQuizId(quizId);
        return ResponseEntity.ok(auditLogs);
    }

    @Operation(
        summary = "Get all quizzes created by, or shared with, a user",
        description = "Provides a list of all quizDTO objects that are shared with the user with authenticated user"
    )
    @GetMapping("/quiz/my-quizzes")
    public ResponseEntity<List<QuizDTO>> getMyQuizzes(Authentication authentication) {
        Long userId = userService.findUserByUsername(authentication.getName()).getId();
        List<QuizDTO> quizzes = quizService.getEditableQuizzesForUser(userId);
        return ResponseEntity.ok(quizzes);
    }

    @Operation(
        summary = "Share a quiz with a user",
        description = "Shares a quiz with a user by providing the quiz id, user id and role"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz shared successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/quiz/{quizId}/share")
    public ResponseEntity<?> shareQuiz(@PathVariable Long quizId,
                                       @RequestParam String userName,
                                       @RequestParam String role,
                                       Authentication authentication) {
        try {
            UserDTO user = userService.findUserByUsername(userName);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found");
            } else if (quizService.getQuizById(quizId) == null) {
                return ResponseEntity.badRequest().body("Quiz not found");
            }

            if (isInvalidSharing(quizId, role, authentication)) {
                return ResponseEntity.badRequest().body("Only the owner of the quiz can share with write permission");
            }

            quizService.shareQuiz(quizId, user.getId(), role.toUpperCase(), authentication.getName());
            return ResponseEntity.ok().body("Quiz shared successfully with user: " + userName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private boolean isInvalidSharing(Long quizId, String role, Authentication authentication) {
        return role.equalsIgnoreCase(Role.EDITOR.name()) && !quizService.getQuizById(quizId).getCreatedByUserId()
                .equals(userService.findUserByUsername(authentication.getName()).getId());
    }
}