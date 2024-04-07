package quizbank.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import quizbank.enums.Category;
import quizbank.enums.DifficultyLevel;
import quizbank.enums.QuestionType;
import quizbank.enums.Role;
import quizbank.model.*;
import quizbank.repository.AuditLogRepository;
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
    private static List<QuizAttempt> getQuizAttempts(User user, Quiz quiz) {
        List<QuizAttempt> quizAttempts = new ArrayList<>();
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setQuiz(quiz);
        quizAttempt.setUser(user);
        quizAttempt.setCorrectAnswers(2);
        quizAttempt.setTotalQuestions(3);
        quizAttempt.setAttemptTime(LocalDateTime.now().minusHours(quiz.getId()));
        quizAttempts.add(quizAttempt);
        QuizAttempt quizAttempt2 = new QuizAttempt();
        quizAttempt2.setQuiz(quiz);
        quizAttempt2.setUser(user);
        quizAttempt2.setCorrectAnswers(1);
        quizAttempt2.setTotalQuestions(3);
        quizAttempt2.setAttemptTime(LocalDateTime.now().minusHours(1 + quiz.getId()));
        quizAttempts.add(quizAttempt2);
        QuizAttempt quizAttempt3 = new QuizAttempt();
        quizAttempt3.setQuiz(quiz);
        quizAttempt3.setUser(user);
        quizAttempt3.setCorrectAnswers(3);
        quizAttempt3.setTotalQuestions(3);
        quizAttempt3.setAttemptTime(LocalDateTime.now().minusHours(5 + quiz.getId()));
        quizAttempts.add(quizAttempt3);
        return quizAttempts;
    }

    private static List<AuditLogEntry> getAuditLogEntries(Quiz quiz, User user) {
        List<AuditLogEntry> auditLogEntries = new ArrayList<>();
        AuditLogEntry auditLogEntry = new AuditLogEntry();
        auditLogEntry.setQuizId(quiz.getId());
        auditLogEntry.setAction("Quiz created");
        auditLogEntry.setTimestamp(LocalDateTime.now().minusHours(quiz.getId()));
        auditLogEntry.setUsername(user.getUsername());
        auditLogEntries.add(auditLogEntry);
        AuditLogEntry auditLogEntry2 = new AuditLogEntry();
        auditLogEntry2.setQuizId(quiz.getId());
        auditLogEntry2.setAction("Quiz edited");
        auditLogEntry2.setTimestamp(LocalDateTime.now().minusHours(1 + quiz.getId()));
        auditLogEntry2.setUsername(user.getUsername());
        auditLogEntries.add(auditLogEntry2);
        AuditLogEntry auditLogEntry3 = new AuditLogEntry();
        auditLogEntry3.setQuizId(quiz.getId());
        auditLogEntry3.setAction("Quiz deleted");
        auditLogEntry3.setTimestamp(LocalDateTime.now().minusHours(5 + quiz.getId()));
        auditLogEntry3.setUsername(user.getUsername());
        auditLogEntries.add(auditLogEntry3);
        return auditLogEntries;
    }

    @Bean
    CommandLineRunner initDatabase(QuizRepository quizRepository, UserRepository userRepository, QuizAttemptRepository quizAttemptRepository, AuditLogRepository auditLogRepository) {
        return args -> {
            User user = userRepository.findByUsername("testuser").orElseGet(() -> {
                User newUser = new User("testuser", new BCryptPasswordEncoder().encode("password"));
                return userRepository.save(newUser);
            });

            Quiz quiz1 = new Quiz();
            quiz1.setName("Quizbank project");
            quiz1.setId(1L);
            quiz1.setCreatedByUser(user);
            quiz1.setCategory(Category.TECHNOLOGY);
            quiz1.setDifficultyLevel(DifficultyLevel.EASY);
            List<Question> questions = Arrays.asList(new Question(quiz1, "Who is Johannes?", "Lorentzsen", QuestionType.FILL_IN_THE_BLANKS), new Question(quiz1, "Where is Sondre?", "Hemsedal", QuestionType.FILL_IN_THE_BLANKS), new Question(quiz1, "What is Jens doing?", "Coding", QuestionType.FILL_IN_THE_BLANKS));
            questions.get(0).setTags(new HashSet<>(Arrays.asList("johannes", "developer")));
            questions.get(1).setTags(new HashSet<>(Arrays.asList("sondre", "developer")));
            questions.get(2).setTags(new HashSet<>(Arrays.asList("jens", "developer", "coding")));
            quiz1.setQuestions(questions);

            Quiz quiz2 = new Quiz();
            quiz2.setName("Trondheim quiz");
            quiz2.setId(2L);
            quiz2.setCreatedByUser(user);
            quiz2.setCategory(Category.GEOGRAPHY);
            quiz2.setDifficultyLevel(DifficultyLevel.MEDIUM);

            List<Question> questions2 = Arrays.asList(new Question(quiz2, "What is Trondheims national dish?", "Sodd", QuestionType.MULTIPLE_CHOICE, Arrays.asList("Sodd", "Pizza", "Burger", "Pasta")), new Question(quiz2, "Trondheim is the capital of Norway?", "No", QuestionType.MULTIPLE_CHOICE, Arrays.asList("Yes", "No")), new Question(quiz2, "Is Gløshaugen better than Dragvoll?", "Yes", QuestionType.MULTIPLE_CHOICE, Arrays.asList("Yes", "No")));
            quiz2.setQuestions(questions2);

            quizRepository.saveAll(Arrays.asList(quiz1, quiz2));

            List<QuizAttempt> quizAttempts = getQuizAttempts(user, quiz1);
            quizAttemptRepository.saveAll(quizAttempts);
            List<QuizAttempt> quizAttempts2 = getQuizAttempts(user, quiz2);
            quizAttemptRepository.saveAll(quizAttempts2);

            auditLogRepository.saveAll(getAuditLogEntries(quiz1, user));
            auditLogRepository.saveAll(getAuditLogEntries(quiz2, user));

            // Create Users
            User user1 = userRepository.findByUsername("testuser1").orElseGet(() -> {
                User newUser = new User("testuser1", new BCryptPasswordEncoder().encode("password"));
                return userRepository.save(newUser);
            });

            User user2 = userRepository.findByUsername("testuser2").orElseGet(() -> {
                User newUser = new User
                        ("testuser2", new BCryptPasswordEncoder().encode("password"));
                return userRepository.save(newUser);
            });

            // Create Quizzes
            Quiz quiz3 = new Quiz();
            quiz3.setId(3L);
            quiz3.setName("Common Knowledge");
            quiz3.setCategory(Category.GENERAL_KNOWLEDGE);
            quiz3.setDifficultyLevel(DifficultyLevel.EASY);
            quiz3.setCreatedByUser(user1);
            List<Question> questionsQuiz3 = new ArrayList<>();
            questionsQuiz3.add(new Question(quiz3, "What is the capital of France?", "Paris", QuestionType.MULTIPLE_CHOICE, Arrays.asList("Paris", "London", "Berlin", "Madrid")));
            questionsQuiz3.add(new Question(quiz3, "Who painted the Mona Lisa?", "Leonardo da Vinci", QuestionType.FILL_IN_THE_BLANKS));
            questionsQuiz3.add(new Question(quiz3, "Is the earth flat?", "FALSE", QuestionType.TRUE_FALSE));
            quiz3.setQuestions(questionsQuiz3);

            questionsQuiz3.get(0).setTags(new HashSet<>(Arrays.asList("france")));
            questionsQuiz3.get(1).setTags(new HashSet<>(Arrays.asList("mona lisa", "painting")));

            Quiz quiz4 = new Quiz();
            quiz4.setId(4L);
            quiz4.setName("Sicence Quiz");
            quiz4.setCategory(Category.SCIENCE);
            quiz4.setDifficultyLevel(DifficultyLevel.HARD);
            quiz4.setCreatedByUser(user2);
            List<Question> questionsQuiz4 = new ArrayList<>();
            questionsQuiz4.add(new Question(quiz4, "What is the si unit of force?", "Newton", QuestionType.MULTIPLE_CHOICE, Arrays.asList("Newton", "Joule", "Watt", "Ampere")));
            questionsQuiz4.add(new Question(quiz4, "What is the atomic number of carbon?", "6", QuestionType.FILL_IN_THE_BLANKS));
            questionsQuiz4.get(0).setTags(new HashSet<>(Arrays.asList("si unit", "force")));
            questionsQuiz4.get(1).setTags(new HashSet<>(Arrays.asList("carbon")));
            quiz4.setQuestions(questionsQuiz4);
            quiz4.addUserWithRole(user, Role.VIEWER);
            quiz4.addUserWithRole(user1, Role.EDITOR);

            quizRepository.saveAll(Arrays.asList(quiz3, quiz4));


            getQuizAttempts(user1, quiz3).forEach(quizAttemptRepository::save);
            getQuizAttempts(user2, quiz4).forEach(quizAttemptRepository::save);
            getAuditLogEntries(quiz3, user1).forEach(auditLogRepository::save);

            List.of(user, user1, user2).forEach(u -> {
                QuizAttempt quizAttempt = new QuizAttempt();
                quizAttempt.setQuiz(quiz4);
                quizAttempt.setUser(u);
                quizAttempt.setCorrectAnswers(1);
                quizAttempt.setTotalQuestions(2);
                quizAttempt.setAttemptTime(LocalDateTime.now());
                quizAttemptRepository.save(quizAttempt);
            });

            Quiz quiz5 = new Quiz();
            quiz5.setId(5L);
            quiz5.setName("Computer Science");
            quiz5.setCategory(Category.TECHNOLOGY);
            quiz5.setDifficultyLevel(DifficultyLevel.HARD);
            quiz5.setCreatedByUser(user2);
            List<Question> questionsQuiz5 = new ArrayList<>();
            questionsQuiz5.add(new Question(quiz5, "What is Java?", "Programming language", QuestionType.MULTIPLE_CHOICE, Arrays.asList("Programming language", "Coffee")));
            questionsQuiz5.add(new Question(quiz5, "Can you use CSS to style a webpage?", "TRUE", QuestionType.TRUE_FALSE));
            questionsQuiz5.forEach(q -> q.setTags(new HashSet<>(Arrays.asList("developer", "coding"))));
            quiz5.setQuestions(questionsQuiz5);
            quizRepository.save(quiz5);
        };
    }
}