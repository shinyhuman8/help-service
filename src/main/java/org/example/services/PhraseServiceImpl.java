package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.DTO.PhraseDTO;
import org.example.Subscriber;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepositoryImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhraseServiceImpl {
    private final MotivationRepositoryImpl motivationRepository;

    @Subscriber
    public void savePhrase(String phrase) {
        motivationRepository.save(new Phrase(UUID.randomUUID(),phrase));
    }


    public Phrase showAnyPhrase() {
        return motivationRepository.show();
    }

//    public void doResponse(PhraseDTO receivedPhraseDTO) throws IOException {
//        savePhrase(convertToPhrase(receivedPhraseDTO));
//    }

    public Phrase convertToPhrase(PhraseDTO phraseDTO) {
        Phrase phrase = new Phrase();
        phrase.setPhrase(phraseDTO.getPhrase());
        return phrase;
    }
    private void enrichPhrase(Phrase phrase) {
        phrase.setId(UUID.randomUUID());
    }

}
