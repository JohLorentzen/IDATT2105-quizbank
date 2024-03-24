package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
