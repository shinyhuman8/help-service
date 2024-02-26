package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.example.DTO.PhraseDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {
    private final ObjectMapper objectMapper;
    private final PhraseService phraseService;

    public Consumer(ObjectMapper objectMapper, PhraseService phraseService) {
        this.objectMapper = objectMapper;
        this.phraseService = phraseService;
    }

    @KafkaListener(topics = "phrase", groupId = "default")
    public void consumeMessage(String message) {
        log.info("message consumed {}", message);
        try {
            System.out.println(message);
            PhraseDTO phraseDTO = objectMapper.readValue(message, PhraseDTO.class);
            phraseService.savePhraseRepo(phraseDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
