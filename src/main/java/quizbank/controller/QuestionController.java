package quizbank.controller;

import quizbank.dto.QuestionDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class QuestionController {


    //TODO: Might not be needed, due to the QuizController
    @GetMapping("/questions/{quizId}")
    public List<QuestionDTO> getQuestionsForQuiz(@PathVariable("quizId") int quizId) {
        return new ArrayList<>();
    }
}
