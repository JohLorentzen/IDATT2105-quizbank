package quizbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizbank.model.AuditLogEntry;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLogEntry, Long> {
    List<AuditLogEntry> findByQuizId(Long quizId);
}
