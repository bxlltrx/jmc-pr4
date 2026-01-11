package com.example.libraryrest.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfig {

    @Bean
    public ObjectMapper jmsObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter(ObjectMapper jmsObjectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(jmsObjectMapper);
        converter.setTargetType(MessageType.TEXT);

        // Можно оставить, но если будут проблемы с type id — закомментируй эту строку
        converter.setTypeIdPropertyName("_type");

        return converter;
    }
}
