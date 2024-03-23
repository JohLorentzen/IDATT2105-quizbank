package quizbank.controller;

import quizbank.model.Question;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class QuestionController {

    List<Question> questions = Arrays.asList(
            new Question(1, "Who is Johannes?", "Lorentzsen"),
            new Question(2, "Where is Sondre?", "Hemsedal"),
            new Question(3, "What is Jens doing?", "Coding")
    );

    List<Question> questions2 = Arrays.asList(
            new Question(4, "What is Trondheims national dish?", "Sodd"),
            new Question(5, "Trondheim is the capital of Norway?", "No"),
            new Question(6, "Is Gløshaugen better than Dragvoll?", "Yes")
    );


    //TODO: Might not be needed, due to the QuizController
    @GetMapping("/questions/{quizId}")
    public List<Question> getQuestionsForQuiz(@PathVariable("quizId") int quizId) {
        if (quizId == 1) {
            return questions;
        } else if (quizId == 2) {
            return questions2;
        } else {
            throw new IllegalArgumentException("Invalid quiz ID");
        }
    }
}
