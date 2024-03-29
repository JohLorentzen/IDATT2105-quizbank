package quizbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import quizbank.dto.QuizDTO;
import quizbank.enums.Category;
import quizbank.model.Quiz;
import quizbank.service.QuizService;
import quizbank.service.UserService;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    @GetMapping("/quiz")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<QuizDTO> quizDTOs = quizService.getAllQuizzes();
        return new ResponseEntity<>(quizDTOs, HttpStatus.OK);
    }

    @PostMapping("/quiz")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrUpdateQuiz(@RequestBody QuizDTO quiz) {
        QuizDTO savedQuiz = quizService.createOrUpdateQuiz(quiz);
        if (savedQuiz.getQuizId() != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/quiz/{quizId}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/quiz/{quizId}")
    public QuizDTO getQuiz(@PathVariable("quizId") Long quizId) {
        return quizService.getQuizById(quizId);
    }

    @GetMapping("/quiz/user/{userId}")
    public ResponseEntity<List<QuizDTO>> getQuizzesCreatedByUser(@PathVariable("userId") Long userId) {
        List<Quiz> quizzesCreatedByUserId = quizService.getQuizzesCreatedByUserId(userId);
        List<QuizDTO> list = quizzesCreatedByUserId.stream().map(quiz -> quizService.toDto(quiz)).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @GetMapping("/quiz/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = Stream.of(Category.values()).map(Category::name).toList();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}