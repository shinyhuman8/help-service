package org.example.services;

import jakarta.servlet.http.HttpServletResponse;
import org.example.DTO.PhraseDTO;
import org.example.annotations.Logged;
import org.example.models.Phrase;

import java.io.IOException;

public interface PhraseService {
    @Logged
    void savePhrase(Phrase phrase);

    @Logged
    Phrase showAnyPhrase();

    @Logged
    void doResponse(PhraseDTO phraseDTO, HttpServletResponse response) throws IOException;

    Phrase convertToPhrase(PhraseDTO phraseDTO);
}
