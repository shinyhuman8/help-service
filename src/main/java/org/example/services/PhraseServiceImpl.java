package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepositoryImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
@RequiredArgsConstructor
public class PhraseServiceImpl {
    private final MotivationRepositoryImpl motivationRepository;

    public void savePhrase(Phrase phrase) {
        motivationRepository.save(phrase);
    }


    public Phrase showAnyPhrase() {
        return motivationRepository.show();
    }

    public void doResponse(PhraseDTO receivedPhraseDTO) throws IOException {
        savePhrase(convertToPhrase(receivedPhraseDTO));
    }

    public Phrase convertToPhrase(PhraseDTO phraseDTO) {
        Phrase phrase = new Phrase();
        phrase.setPhrase(phraseDTO.getPhrase());
        return phrase;
    }

}
