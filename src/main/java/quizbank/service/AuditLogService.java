package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quizbank.model.AuditLogEntry;
import quizbank.repository.AuditLogRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing audit logs.
 * This class provides methods for logging actions and finding logs by quiz ID.
 */
@Service
public class AuditLogService {
    @Autowired
    private final AuditLogRepository auditLogRepository;

    /**
     * Constructor for the AuditLogService.
     *
     * @param auditLogRepository the repository for audit logs
     */
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * Logs an action.
     *
     * @param quizId the ID of the quiz
     * @param action the action to be logged
     * @param username the username of the user performing the action
     */
    public void logAction(Long quizId, String action, String username) {
        AuditLogEntry entry = new AuditLogEntry();
        entry.setQuizId(quizId);
        entry.setAction(action);
        entry.setTimestamp(LocalDateTime.now());
        entry.setUsername(username);
        auditLogRepository.save(entry);
    }

    /**
     * Finds audit log entries by quiz ID.
     *
     * @param quizId the ID of the quiz
     * @return a list of audit log entries for the specified quiz
     */
    public List<AuditLogEntry> findByQuizId(Long quizId) {
        return auditLogRepository.findByQuizId(quizId);
    }
}