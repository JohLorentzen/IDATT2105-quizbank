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
import quizbank.enums.Category;
import quizbank.model.Quiz;
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
            @ApiResponse(responseCode = "204", description = "Updated quiz successfully"),
            @ApiResponse(responseCode = "201", description = "Created a new quiz successfully")
    })
    @PostMapping("/quiz")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrUpdateQuiz(Authentication authentication, @RequestBody QuizDTO quiz) {
        if (quiz.getCreatedByUserId() == null) {
            quiz.setCreatedByUserId(userService.findUserByUsername(authentication.getName()).getId());
        }
        QuizDTO savedQuiz = quizService.createOrUpdateQuiz(quiz);
        if (savedQuiz.getQuizId() != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @Operation(
        summary = "Deletes a quiz by its id",
        description = "Deletes a quiz based on its id"
    )
    @ApiResponse(responseCode = "204", description = "Deleted the quiz successfully")
    @DeleteMapping("/quiz/{quizId}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
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
}