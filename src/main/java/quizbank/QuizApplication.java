package quizbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the QuizBank application.
 * This class is responsible for starting the Spring Boot application.
 * <p>
 * The application is a quiz bank that allows users to create quizzes,
 * share them with other users, see audit logs of quizzes,
 * take quizzes and view their quiz progress.
 * </p>
 */
@SpringBootApplication
public class QuizApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(QuizApplication.class, args);
    }
}