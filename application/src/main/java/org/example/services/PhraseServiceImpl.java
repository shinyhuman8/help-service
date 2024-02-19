package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.DTO.PhraseDTO;
import org.example.MessageBroker;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhraseServiceImpl implements PhraseService {
    private final MotivationRepository motivationRepository;
    private final MessageBroker messageBroker;


    @Override
    public void savePhrase(PhraseDTO phraseDTO) {
        messageBroker.sendMessage(convertToPhrase(phraseDTO));
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
