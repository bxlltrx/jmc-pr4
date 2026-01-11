package com.example.libraryrest.service;

import com.example.libraryrest.jms.ChangeEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChangeEventPublisher {

    private final JmsTemplate jmsTemplate;
    private final String changesQueue;

    public ChangeEventPublisher(JmsTemplate jmsTemplate,
                                @Value("${app.jms.queue.changes}") String changesQueue) {
        this.jmsTemplate = jmsTemplate;
        this.changesQueue = changesQueue;
    }

    public void publish(ChangeEvent event) {
        jmsTemplate.convertAndSend(changesQueue, event);
    }
}
