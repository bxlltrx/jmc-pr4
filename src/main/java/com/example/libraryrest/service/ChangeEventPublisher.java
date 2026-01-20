package com.example.libraryrest.service;

import com.example.libraryrest.jms.ChangeEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChangeEventPublisher {

    private final JmsTemplate topicJmsTemplate;
    private final String changeTopic;

    public ChangeEventPublisher(
            JmsTemplate topicJmsTemplate,
            @Value("${app.jms.change-topic}") String changeTopic
    ) {
        this.topicJmsTemplate = topicJmsTemplate;
        this.changeTopic = changeTopic;
    }

    public void publish(ChangeEvent event) {
        topicJmsTemplate.convertAndSend(changeTopic, event);
    }
}