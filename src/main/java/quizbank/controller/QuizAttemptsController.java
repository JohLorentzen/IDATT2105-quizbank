package quizbank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quizbank.dto.QuizAttemptDTO;
import quizbank.service.QuizAttemptService;

import java.util.List;

@Tag(
        name = "Quiz Attempts Controller",
        description = "Record quiz attempts and retrieve quiz attempt statistics."
)
@RestController
@RequestMapping("/rest")
public class QuizAttemptsController {
    @Autowired
    private QuizAttemptService quizAttemptService;

    @Operation(summary = "Record a quiz attempt")
    @ApiResponse(responseCode = "200", description = "Successfully recorded quiz attempt")
    @PostMapping("/quiz-attempts")
    public ResponseEntity<?> recordQuizAttempt(@RequestBody QuizAttemptDTO attemptDto) {
        quizAttemptService.recordQuizAttempt(attemptDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get quiz attempts for a user")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved quiz attempts")
    @GetMapping("/quiz-attempts/{userId}")
    public ResponseEntity<List<QuizAttemptDTO>> getUserQuizAttempts(@PathVariable Long userId) {
        List<QuizAttemptDTO> attempts = quizAttemptService.getUserQuizAttempts(userId);
        return ResponseEntity.ok(attempts);
    }

}