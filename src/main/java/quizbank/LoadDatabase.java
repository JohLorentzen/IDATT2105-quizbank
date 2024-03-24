package quizbank;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quizbank.model.Question;
import quizbank.model.Quiz;
import quizbank.repository.QuizRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(QuizRepository repository) {
        return args -> {
            Quiz quiz1 = new Quiz();
            quiz1.setName("Quiz 1");
            quiz1.setId(1);
            quiz1.setCreatedByUserId(1);
            List<Question> questions = Arrays.asList(
                    new Question(quiz1, "Who is Johannes?", "Lorentzsen"),
                    new Question(quiz1, "Where is Sondre?", "Hemsedal"),
                    new Question(quiz1, "What is Jens doing?", "Coding")
            );
            quiz1.setQuestions(questions);

            Quiz quiz2 = new Quiz();
            quiz2.setName("Trondheim quiz");
            quiz2.setId(2);
            quiz2.setCreatedByUserId(2);

            List<Question> questions2 = Arrays.asList(
                    new Question(quiz2, "What is Trondheims national dish?", "Sodd"),
                    new Question(quiz2, "Trondheim is the capital of Norway?", "No"),
                    new Question(quiz2, "Is Gløshaugen better than Dragvoll?", "Yes")
            );
            quiz2.setQuestions(questions2);


            repository.saveAll(Arrays.asList(quiz1, quiz2));
        };
    }

}