package org.example.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.DTO.PhraseDTO;
import org.example.services.Consumer;
import org.example.services.PhraseService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerImpl implements Consumer {
    private final ObjectMapper objectMapper;
    private final PhraseService phraseService;

    public ConsumerImpl(ObjectMapper objectMapper, PhraseService phraseService) {
        this.objectMapper = objectMapper;
        this.phraseService = phraseService;
    }

    @Override
    @KafkaListener(topics = "phrase", groupId = "default")
    public void consumeMessage(String message) {
        log.info("Message consumed {}", message);
        try {
            System.out.println(message);
            PhraseDTO phraseDTO = objectMapper.readValue(message, PhraseDTO.class);
            phraseService.savePhraseRepo(phraseDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
