package org.example.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.configurations.AppConfig;
import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepository;
import org.example.services.PhraseService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnBean(AppConfig.class)
public class PhraseServiceImpl implements PhraseService {

    private final MotivationRepository motivationRepository;

    public PhraseServiceImpl(MotivationRepository motivationRepository) {
        this.motivationRepository = motivationRepository;
    }

    @Override
    public String savePhrase(PhraseDTO phraseDTO) {
        if (!motivationRepository.checkUniquePhrase(phraseDTO)) {
            savePhraseRepo(phraseDTO);
            return "message send";
        } else {
            return "phrase is not unique in broker " + phraseDTO.getPhrase();
        }
    }


    @Override
    public void savePhraseRepo(PhraseDTO phraseDTO) {
        motivationRepository.save(convertToPhrase(phraseDTO));
        log.info("Phrase save repository... {}", phraseDTO);
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
