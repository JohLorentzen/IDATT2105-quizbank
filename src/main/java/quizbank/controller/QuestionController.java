package quizbank.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import quizbank.dto.QuestionDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(
    name = "Question Controller",
    description = "Get detailed information from a quiz."
)
@RestController
@RequestMapping("/rest")
public class QuestionController {


    //TODO: Might not be needed, due to the QuizController
    //TODO: Update API docs if necessary..
    @GetMapping("/questions/{quizId}")
    public List<QuestionDTO> getQuestionsForQuiz(@PathVariable("quizId") int quizId) {
        return new ArrayList<>();
    }
}
