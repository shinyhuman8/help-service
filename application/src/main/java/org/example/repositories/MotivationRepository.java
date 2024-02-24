package org.example.repositories;

import org.example.Subscriber;
import org.example.models.Phrase;

public interface MotivationRepository {
    @Subscriber
    void save(Phrase phrase);

    Phrase show();
}
