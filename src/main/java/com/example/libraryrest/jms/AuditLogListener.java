package com.example.libraryrest.jms;

import com.example.libraryrest.model.AuditLog;
import com.example.libraryrest.repo.AuditLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class AuditLogListener {

    private final AuditLogRepository auditRepo;
    private final JavaMailSender mailSender;
    private final boolean mailEnabled;
    private final String mailTo;

    public AuditLogListener(
            AuditLogRepository auditRepo,
            JavaMailSender mailSender,
            @Value("${app.mail.enabled:false}") boolean mailEnabled,
            @Value("${app.mail.to:}") String mailTo
    ) {
        this.auditRepo = auditRepo;
        this.mailSender = mailSender;
        this.mailEnabled = mailEnabled;
        this.mailTo = mailTo;
    }

    @JmsListener(destination = "${app.jms.queue.changes}")
    public void onMessage(ChangeEvent event) {

        // ====== ДОБАВИЛИ: отладка ======
        System.out.println("=== JMS LISTENER START ===");
        System.out.println("EVENT type=[" + event.getEventType() + "] entity=[" + event.getEntityType() + "] id=[" + event.getEntityId() + "]");
        System.out.println("MAIL enabled=" + mailEnabled + " to=[" + mailTo + "]");
        System.out.println("==========================");
        // ===============================

        // 1) Всегда пишем лог в БД
        AuditLog log = new AuditLog(
                event.getEventType(),
                event.getEntityType(),
                event.getEntityId(),
                event.getDetails(),
                event.getTimestamp() != null ? event.getTimestamp() : OffsetDateTime.now()
        );
        auditRepo.save(log);

        // 2) Условие: шлём email только при DELETE
        if (!mailEnabled) {
            System.out.println("MAIL SKIP: disabled");
            return;
        }
        if (mailTo == null || mailTo.isBlank()) {
            System.out.println("MAIL SKIP: mailTo is blank");
            return;
        }

        String type = event.getEventType() == null ? "" : event.getEventType().trim();
        boolean shouldNotify = "DELETE".equalsIgnoreCase(type);
        if (!shouldNotify) {
            System.out.println("MAIL SKIP: eventType != DELETE, type=[" + type + "]");
            return;
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailTo);
        msg.setSubject("Library: entity changed (" + type + ")");
        msg.setText(
                "Event: " + type + "\n" +
                "Entity: " + event.getEntityType() + "\n" +
                "Id: " + event.getEntityId() + "\n" +
                "Details: " + event.getDetails() + "\n" +
                "Time: " + event.getTimestamp()
        );

        
        try {
            mailSender.send(msg);
            System.out.println("MAIL SENT OK");
        } catch (Exception e) {
            System.out.println("MAIL SEND FAILED: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
}
