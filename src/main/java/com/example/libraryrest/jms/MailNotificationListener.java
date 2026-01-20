package com.example.libraryrest.jms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailNotificationListener {

    private final JavaMailSender mailSender;
    private final String mailTo;
    private final boolean mailEnabled;

    public MailNotificationListener(
            JavaMailSender mailSender,
            @Value("${app.mail.to}") String mailTo,
            @Value("${app.mail.enabled:false}") boolean mailEnabled
    ) {
        this.mailSender = mailSender;
        this.mailTo = mailTo;
        this.mailEnabled = mailEnabled;
    }

    @JmsListener(
            destination = "${app.jms.change-topic}",
            containerFactory = "topicFactory"
    )
    public void onMessage(ChangeEvent event) {

        if (!mailEnabled) return;
        if (!"DELETE".equalsIgnoreCase(event.getEventType())) return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailTo);
        message.setSubject("Library DELETE notification");
        message.setText(
                "Entity: " + event.getEntityType() + "\n" +
                "ID: " + event.getEntityId() + "\n" +
                "Details: " + event.getDetails()
        );

        mailSender.send(message);
    }
}
