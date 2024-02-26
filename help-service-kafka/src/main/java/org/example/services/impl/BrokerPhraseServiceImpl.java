package org.example.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepository;
import org.example.services.PhraseService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrokerPhraseServiceImpl implements PhraseService {
    private final MotivationRepository motivationRepository;
    private final ProducerImpl producer;

    public BrokerPhraseServiceImpl(MotivationRepository motivationRepository, ProducerImpl producer) {
        this.motivationRepository = motivationRepository;
        this.producer = producer;
    }

    @Override
    public String savePhrase(PhraseDTO phraseDTO) {
        if (!motivationRepository.checkUniquePhrase(phraseDTO)) {
            return producer.sendMessage(phraseDTO);
        } else {
            log.info("phrase is not unique in broker {}", phraseDTO);
            return "phrase is not unique in broker " + phraseDTO.getPhrase();
        }
    }

    @Override
    public void savePhraseRepo(PhraseDTO phraseDTO) {
        motivationRepository.save(convertToPhrase(phraseDTO));
        log.info("phrase broker {}", phraseDTO);
    }

    @Override
    public Phrase showAnyPhrase() {
        return motivationRepository.show();
    }

    public Phrase convertToPhrase(PhraseDTO phraseDTO) {
        Phrase phrase = new Phrase();
        phrase.setPhrase(phraseDTO.getPhrase());
        return phrase;
    }
}
