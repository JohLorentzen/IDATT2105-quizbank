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
import quizbank.model.Quiz;
import quizbank.repository.QuizRepository;
import quizbank.service.QuizService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizService quizService;

    @GetMapping("/quiz")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizDTO> quizDTOs = quizzes.stream().map(quizService::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(quizDTOs, HttpStatus.OK);
    }

    @PostMapping("/quiz")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrUpdateQuiz(@RequestBody QuizDTO quiz) {
        Quiz quizEntity = quizService.toEntity(quiz);
        if (quizEntity.getId() != null && quizRepository.existsById(quizEntity.getId())) {
            quizRepository.save(quizEntity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            quizRepository.save(quizEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/quiz/{quizId}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long quizId) {
        quizRepository.deleteById(quizId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/quiz/{quizId}")
    public QuizDTO getQuiz(@PathVariable("quizId") Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID"));
        return quizService.toDto(quiz);
    }
}
