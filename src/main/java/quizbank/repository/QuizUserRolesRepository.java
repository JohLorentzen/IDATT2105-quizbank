package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import quizbank.enums.Role;
import quizbank.model.Quiz;
import quizbank.model.QuizUserRoles;
import quizbank.model.User;

import java.util.List;

public interface QuizUserRolesRepository extends JpaRepository<QuizUserRoles, Long> {

    @Query("SELECT qur.quiz FROM QuizUserRoles qur WHERE qur.user = :user AND qur.role = :role")
    List<Quiz> findQuizzesByUserAndRole(User user, Role role);
}
