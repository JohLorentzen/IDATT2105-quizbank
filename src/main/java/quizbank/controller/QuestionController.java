package quizbank.controller;

import quizbank.dto.QuestionDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class QuestionController {

    List<QuestionDTO> questions = Arrays.asList(
            new QuestionDTO(1, "Who is Johannes?", "Lorentzsen"),
            new QuestionDTO(2, "Where is Sondre?", "Hemsedal"),
            new QuestionDTO(3, "What is Jens doing?", "Coding")
    );

    List<QuestionDTO> questions2 = Arrays.asList(
            new QuestionDTO(4, "What is Trondheims national dish?", "Sodd"),
            new QuestionDTO(5, "Trondheim is the capital of Norway?", "No"),
            new QuestionDTO(6, "Is Gløshaugen better than Dragvoll?", "Yes")
    );


    //TODO: Might not be needed, due to the QuizController
    @GetMapping("/questions/{quizId}")
    public List<QuestionDTO> getQuestionsForQuiz(@PathVariable("quizId") int quizId) {
        if (quizId == 1) {
            return questions;
        } else if (quizId == 2) {
            return questions2;
        } else {
            throw new IllegalArgumentException("Invalid quiz ID");
        }
    }
}
