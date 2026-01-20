package com.example.libraryrest.jms;

import com.example.libraryrest.model.AuditLog;
import com.example.libraryrest.repo.AuditLogRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class AuditLogListener {

    private final AuditLogRepository auditLogRepository;

    public AuditLogListener(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @JmsListener(
            destination = "${app.jms.change-topic}",
            containerFactory = "topicFactory"
    )
    public void onMessage(ChangeEvent event) {
        AuditLog log = new AuditLog(
                event.getEventType(),
                event.getEntityType(),
                event.getEntityId(),
                event.getDetails(),
                event.getTimestamp() != null ? event.getTimestamp() : OffsetDateTime.now()
        );

        auditLogRepository.save(log);
    }
}
