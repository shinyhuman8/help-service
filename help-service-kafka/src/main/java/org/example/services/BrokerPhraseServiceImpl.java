package org.example.services;//package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.AppConfig;
import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrokerPhraseServiceImpl implements PhraseService{
    private final MotivationRepository motivationRepository;
    private final Producer producer;

    public BrokerPhraseServiceImpl(MotivationRepository motivationRepository, Producer producer) {
        this.motivationRepository = motivationRepository;
        this.producer = producer;
    }

    @Override
    public void savePhrase(PhraseDTO phraseDTO) {
        producer.sendMessage(phraseDTO);
    }
    @Override
    public void savePhraseRepo(PhraseDTO phraseDTO) {
        motivationRepository.save(convertToPhrase(phraseDTO));
        log.info("phrase broker {}",phraseDTO);
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
