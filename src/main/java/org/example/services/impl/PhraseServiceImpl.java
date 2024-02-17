package org.example.services.impl;

import jakarta.servlet.http.HttpServletResponse;
import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepository;
import org.example.repositories.MotivationRepositoryImpl;
import org.example.services.PhraseService;

import java.io.IOException;

public class PhraseServiceImpl implements PhraseService {
    private final MotivationRepository motivationRepository = new MotivationRepositoryImpl();

    @Override
    public void savePhrase(Phrase phrase) {
        motivationRepository.save(phrase);
    }


    @Override
    public Phrase showAnyPhrase() {
        return motivationRepository.show();
    }

    @Override
    public void doResponse(PhraseDTO receivedPhraseDTO, HttpServletResponse response) throws IOException {
        if (receivedPhraseDTO.getPhrase() == null || receivedPhraseDTO.getPhrase().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
            response.getWriter().write("Phrase should not be empty");
        } else {
            savePhrase(convertToPhrase(receivedPhraseDTO));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Phrase successfully saved");
        }
    }

    @Override
    public Phrase convertToPhrase(PhraseDTO phraseDTO) {
        Phrase phrase = new Phrase();
        phrase.setPhrase(phraseDTO.getPhrase());
        return phrase;
    }


}
