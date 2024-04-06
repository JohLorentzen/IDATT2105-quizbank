package quizbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import quizbank.model.AuditLogEntry;
import quizbank.repository.AuditLogRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuditLogServiceTest {

    @InjectMocks
    private AuditLogService auditLogService;

    @Mock
    private AuditLogRepository auditLogRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void logActionCreatesNewEntry() {
        Long quizId = 1L;
        String action = "action";
        String username = "username";

        auditLogService.logAction(quizId, action, username);

        verify(auditLogRepository, times(1)).save(any(AuditLogEntry.class));
    }

    @Test
    public void findByQuizIdReturnsEntries() {
        Long quizId = 1L;
        AuditLogEntry entry = new AuditLogEntry();
        entry.setQuizId(quizId);
        entry.setAction("action");
        entry.setTimestamp(LocalDateTime.now());
        entry.setUsername("username");

        when(auditLogRepository.findByQuizId(quizId)).thenReturn(Collections.singletonList(entry));

        List<AuditLogEntry> entries = auditLogService.findByQuizId(quizId);

        assertEquals(1, entries.size());
        assertEquals(entry, entries.get(0));
    }

    @Test
    public void findByQuizIdReturnsEmptyListWhenNoEntries() {
        Long quizId = 1L;

        when(auditLogRepository.findByQuizId(quizId)).thenReturn(Collections.emptyList());

        List<AuditLogEntry> entries = auditLogService.findByQuizId(quizId);

        assertEquals(0, entries.size());
    }
}