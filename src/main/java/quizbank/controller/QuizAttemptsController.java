package quizbank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import quizbank.dto.QuizAttemptDTO;
import quizbank.service.QuizAttemptService;
import quizbank.service.UserService;

import java.time.LocalDateTime;
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

    @Autowired
    private UserService userService;

    @Operation(summary = "Record a quiz attempt")
    @ApiResponse(responseCode = "200", description = "Successfully recorded quiz attempt")
    @PostMapping("/quiz-attempts")
    public ResponseEntity<?> recordQuizAttempt(@RequestBody QuizAttemptDTO attemptDto, Authentication authentication) {
        attemptDto.setAttemptTime(LocalDateTime.now());
        attemptDto.setUserId(userService.findUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername()).getId());
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