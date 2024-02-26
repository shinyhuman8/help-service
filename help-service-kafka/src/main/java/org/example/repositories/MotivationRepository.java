package org.example.repositories;

import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;

public interface MotivationRepository {
    void save(Phrase phrase);

    Phrase show();
    boolean checkUniquePhrase(PhraseDTO phrase);
}
