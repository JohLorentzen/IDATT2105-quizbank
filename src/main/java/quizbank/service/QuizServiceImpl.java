package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import quizbank.dto.QuizDTO;
import quizbank.enums.Category;
import quizbank.enums.DifficultyLevel;
import quizbank.model.Quiz;
import org.springframework.stereotype.Service;
import quizbank.model.User;
import quizbank.repository.QuizRepository;

import java.util.Collections;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;

    private final UserService userService;
    private final QuizRepository quizRepository;

    @Autowired
    public QuizServiceImpl(QuestionService questionService, UserService userService, QuizRepository quizRepository) {
        this.questionService = questionService;
        this.userService = userService;
        this.quizRepository = quizRepository;
    }

    @Override
    public List<Quiz> getQuizzesCreatedByUserId(Long userName) {
        User user = userService.findUserById(userName);
        if (user == null) {
            return Collections.emptyList();
        }
        return quizRepository.findByCreatedBy(user);
    }

    @Override
    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(this::toDto).toList();
    }

    @Override
    public QuizDTO createOrUpdateQuiz(QuizDTO quiz) {
        Quiz quizEntity = toEntity(quiz);
        quizRepository.save(quizEntity);
        return toDto(quizEntity);
    }

    @Override
    public void deleteQuiz(Long quizId) {
        quizRepository.deleteById(quizId);
    }

    @Override
    public QuizDTO getQuizById(Long quizId) {
        return quizRepository.findById(quizId).map(this::toDto).orElse(null);
    }

    @Override
    public QuizDTO toDto(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setQuizId(quiz.getId());
        dto.setQuizName(quiz.getName());
        dto.setCreatedByUserId(quiz.getCreatedBy().getId());
        dto.setQuestions(new QuestionServiceImpl().toDto(quiz.getQuestions()));
        dto.setCategory(quiz.getCategory().toString());
        dto.setDifficultyLevel(quiz.getDifficultyLevel().toString());
        return dto;
    }

    @Override
    public Quiz toEntity(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setId(quizDTO.getQuizId());
        quiz.setName(quizDTO.getQuizName());
        quiz.setCreatedByUser(userService.findUserById(quizDTO.getCreatedByUserId()));
        quiz.setQuestions(questionService.toEntity(quizDTO.getQuestions(), quiz));
        quiz.setCategory(Category.valueOf(quizDTO.getCategory()));
        quiz.setDifficultyLevel(DifficultyLevel.valueOf(quizDTO.getDifficultyLevel()));
        return quiz;
    }

}
