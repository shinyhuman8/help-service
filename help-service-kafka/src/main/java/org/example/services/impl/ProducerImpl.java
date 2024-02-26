package org.example.services.impl;

import org.example.DTO.PhraseDTO;
import org.example.services.Producer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerImpl implements Producer {
    private final KafkaTemplate<String, PhraseDTO> kafkaTemplate;

    public ProducerImpl(KafkaTemplate<String, PhraseDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String sendMessage(PhraseDTO phrase) {
        kafkaTemplate.send("phrase", phrase);
        return "message send";
    }
}
