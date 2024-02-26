package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.AppConfig;
import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnBean(AppConfig.class)
public class PhraseServiceImpl implements PhraseService {

    private final MotivationRepository motivationRepository;
    private final Producer producer;

    public PhraseServiceImpl(MotivationRepository motivationRepository, Producer producer) {
        this.motivationRepository = motivationRepository;
        this.producer = producer;
    }

    //    public PhraseServiceImpl(MotivationRepository motivationRepository) {
//        this.motivationRepository = motivationRepository;
//    }

    @Override
    public void savePhrase(PhraseDTO phraseDTO) {
        producer.sendMessage(phraseDTO);
    }
//    @Override
//    public void savePhrase(PhraseDTO phraseDTO) {
//        motivationRepository.save(convertToPhrase(phraseDTO));
//    }

    @Override
    public void savePhraseRepo(PhraseDTO phraseDTO) {
        motivationRepository.save(convertToPhrase(phraseDTO));
        log.info("phrase  blabls {}", phraseDTO);
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
