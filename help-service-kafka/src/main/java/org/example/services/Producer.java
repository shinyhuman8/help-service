package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    //@Value("${topic.name}")
    private String orderTopic;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendMessage(PhraseDTO phrase) {
        try {
            kafkaTemplate.send("phrase", objectMapper.writeValueAsString(phrase));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "message send";
    }
}
