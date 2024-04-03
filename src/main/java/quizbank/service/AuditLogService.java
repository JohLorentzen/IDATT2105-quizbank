package quizbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quizbank.model.AuditLogEntry;
import quizbank.repository.AuditLogRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {
    @Autowired
    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void logAction(Long quizId, String action, String username) {
        AuditLogEntry entry = new AuditLogEntry();
        entry.setQuizId(quizId);
        entry.setAction(action);
        entry.setTimestamp(LocalDateTime.now());
        entry.setUsername(username);
        auditLogRepository.save(entry);
    }

    public List<AuditLogEntry> findByQuizId(Long quizId) {
        return auditLogRepository.findByQuizId(quizId);
    }
}
