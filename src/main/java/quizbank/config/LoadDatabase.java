package quizbank.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quizbank.model.Question;
import quizbank.model.Quiz;
import quizbank.model.User;
import quizbank.enums.DifficultyLevel;
import quizbank.enums.Category;
import quizbank.repository.QuizRepository;
import quizbank.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(QuizRepository quizRepository, UserRepository userRepository) {
        return args -> {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            userRepository.save(user);

            Quiz quiz1 = new Quiz();
            quiz1.setName("Quizbank project");
            quiz1.setId(1L);
            quiz1.setCreatedByUser(user);
            quiz1.setCategory(Category.GENERAL_KNOWLEDGE);
            quiz1.setDifficultyLevel(DifficultyLevel.EASY);
            List<Question> questions = Arrays.asList(
                    new Question(quiz1, "Who is Johannes?", "Lorentzsen"),
                    new Question(quiz1, "Where is Sondre?", "Hemsedal"),
                    new Question(quiz1, "What is Jens doing?", "Coding")
            );
            quiz1.setQuestions(questions);

            Quiz quiz2 = new Quiz();
            quiz2.setName("Trondheim quiz");
            quiz2.setId(2L);
            quiz2.setCreatedByUser(user);
            quiz2.setCategory(Category.GEOGRAPHY);
            quiz2.setDifficultyLevel(DifficultyLevel.MEDIUM);

            List<Question> questions2 = Arrays.asList(
                    new Question(quiz2, "What is Trondheims national dish?", "Sodd"),
                    new Question(quiz2, "Trondheim is the capital of Norway?", "No"),
                    new Question(quiz2, "Is Gløshaugen better than Dragvoll?", "Yes")
            );
            quiz2.setQuestions(questions2);

            quizRepository.saveAll(Arrays.asList(quiz1, quiz2));
        };
    }
}