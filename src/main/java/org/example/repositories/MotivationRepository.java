package org.example.repositories;

import org.example.annotations.Logged;
import org.example.models.Phrase;

public interface MotivationRepository {
    @Logged
    void save(Phrase phrase);

    @Logged
    Phrase show();
}
