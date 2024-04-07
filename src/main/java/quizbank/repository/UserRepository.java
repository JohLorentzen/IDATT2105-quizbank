package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.model.User;

import java.util.Optional;

/**
 * Repository for managing {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByUsername(String username);
}