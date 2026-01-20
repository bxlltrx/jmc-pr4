package com.example.libraryrest.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);     // JSON в TextMessage
        converter.setTypeIdPropertyName("_type");      // тип для обратной конвертации
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public DefaultJmsListenerContainerFactory topicFactory(
            ConnectionFactory connectionFactory,
            MappingJackson2MessageConverter jacksonJmsMessageConverter
    ) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);                 // TOPIC
        factory.setMessageConverter(jacksonJmsMessageConverter); // ✅ важно для @JmsListener
        return factory;
    }

    @Bean
    public JmsTemplate topicJmsTemplate(
            ConnectionFactory connectionFactory,
            MappingJackson2MessageConverter jacksonJmsMessageConverter
    ) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(true);                // TOPIC
        template.setMessageConverter(jacksonJmsMessageConverter); // ✅ важно для convertAndSend
        return template;
    }
}
