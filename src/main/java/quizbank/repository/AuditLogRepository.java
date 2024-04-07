package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.model.AuditLogEntry;

import java.util.List;

/**
 * Repository for managing {@link AuditLogEntry} entities.
 */
public interface AuditLogRepository extends JpaRepository<AuditLogEntry, Long> {

    /**
     * Finds all audit log entries associated with a specific quiz.
     *
     * @param quizId the ID of the quiz
     * @return a list of audit log entries for the specified quiz
     */
    List<AuditLogEntry> findByQuizId(Long quizId);
}