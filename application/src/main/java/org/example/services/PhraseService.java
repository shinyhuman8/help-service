package org.example.services;

import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;

public interface PhraseService {
    void savePhrase(PhraseDTO phraseDTO);

    Phrase showAnyPhrase();
}
