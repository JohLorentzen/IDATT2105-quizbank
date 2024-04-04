package quizbank.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quizbank.model.Question;
import quizbank.model.Quiz;
import quizbank.model.QuizAttempt;
import quizbank.model.User;
import quizbank.enums.DifficultyLevel;
import quizbank.enums.Category;
import quizbank.enums.QuestionType;
import quizbank.repository.QuizAttemptRepository;
import quizbank.repository.QuizRepository;
import quizbank.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(QuizRepository quizRepository, UserRepository userRepository, QuizAttemptRepository quizAttemptRepository) {
        return args -> {
            User user = userRepository.findByUsername("testuser").orElseGet(() -> {
                User newUser = new User();
                newUser.setUsername("testuser");
                newUser.setPassword("password");
                return userRepository.save(newUser);
            });

            Quiz quiz1 = new Quiz();
            quiz1.setName("Quizbank project");
            quiz1.setId(1L);
            quiz1.setCreatedByUser(user);
            quiz1.setCategory(Category.GENERAL_KNOWLEDGE);
            quiz1.setDifficultyLevel(DifficultyLevel.EASY);
            List<Question> questions = Arrays.asList(
                    new Question(quiz1, "Who is Johannes?", "Lorentzsen", QuestionType.FILL_IN_THE_BLANKS),
                    new Question(quiz1, "Where is Sondre?", "Hemsedal", QuestionType.FILL_IN_THE_BLANKS),
                    new Question(quiz1, "What is Jens doing?", "Coding", QuestionType.FILL_IN_THE_BLANKS)
            );
            questions.get(0).setTags(new HashSet<>(Arrays.asList("johannes", "developer")));
            questions.get(1).setTags(new HashSet<>(Arrays.asList("sondre", "developer")));
            questions.get(2).setTags(new HashSet<>(Arrays.asList("jens", "developer")));
            quiz1.setQuestions(questions);

            Quiz quiz2 = new Quiz();
            quiz2.setName("Trondheim quiz");
            quiz2.setId(2L);
            quiz2.setCreatedByUser(user);
            quiz2.setCategory(Category.GEOGRAPHY);
            quiz2.setDifficultyLevel(DifficultyLevel.MEDIUM);

            List<Question> questions2 = Arrays.asList(
                    new Question(quiz2, "What is Trondheims national dish?", "Sodd", QuestionType.MULTIPLE_CHOICE, Arrays.asList("Sodd", "Pizza", "Burger", "Pasta")),
                    new Question(quiz2, "Trondheim is the capital of Norway?", "No", QuestionType.MULTIPLE_CHOICE, Arrays.asList("Yes", "No")),
                    new Question(quiz2, "Is Gløshaugen better than Dragvoll?", "Yes", QuestionType.MULTIPLE_CHOICE, Arrays.asList("Yes", "No"))
            );
            quiz2.setQuestions(questions2);

            quizRepository.saveAll(Arrays.asList(quiz1, quiz2));

            List<QuizAttempt> quizAttempts = getQuizAttempts(user, quiz1);
            quizAttemptRepository.saveAll(quizAttempts);
        };
    }

    private static List<QuizAttempt> getQuizAttempts(User user, Quiz quiz1) {
        List<QuizAttempt> quizAttempts = new ArrayList<>();
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setQuiz(quiz1);
        quizAttempt.setUser(user);
        quizAttempt.setCorrectAnswers(2);
        quizAttempt.setTotalQuestions(3);
        quizAttempt.setAttemptTime(LocalDateTime.now());
        quizAttempts.add(quizAttempt);
        QuizAttempt quizAttempt2 = new QuizAttempt();
        quizAttempt2.setQuiz(quiz1);
        quizAttempt2.setUser(user);
        quizAttempt2.setCorrectAnswers(1);
        quizAttempt2.setTotalQuestions(3);
        quizAttempt2.setAttemptTime(LocalDateTime.now().plusHours(1));
        quizAttempts.add(quizAttempt2);
        return quizAttempts;
    }
}