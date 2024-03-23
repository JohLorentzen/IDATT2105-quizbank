package quizbank.controller;

import quizbank.model.Question;
import quizbank.model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class QuizController {

    private List<Question> questions = Arrays.asList(
            new Question(1, "Who is Johannes?", "Lorentzsen"),
            new Question(2, "Where is Sondre?", "Hemsedal"),
            new Question(3, "What is Jens doing?", "Coding")
    );

    private List<Question> questions2 = Arrays.asList(
            new Question(4, "What is Trondheims national dish?", "Sodd"),
            new Question(5, "Trondheim is the capital of Norway?", "No"),
            new Question(6, "Is Gløshaugen better than Dragvoll?", "Yes")
    );

    private List<Quiz> quizzes = new ArrayList<>(Arrays.asList(
            new Quiz(1, "Developer quiz", 101, questions),
            new Quiz(2, "Trondheim quiz", 102, questions2)
    ));

    @GetMapping("/quiz")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/quiz/{quizId}")
    public Quiz getQuiz(@PathVariable("quizId") int quizId) {
        if (quizId == 1) {
            return quizzes.get(0);
        } else if (quizId == 2) {
            return quizzes.get(1);
        } else {
            throw new IllegalArgumentException("Invalid quiz ID");
        }
    }

    @PostMapping("/quiz")
    public ResponseEntity createOrUpdateQuiz(@RequestBody Quiz quiz) {
        quizzes.add(quiz);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
