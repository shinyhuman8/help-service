package org.example.repositories;

import org.example.models.Phrase;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class MotivationRepositoryImpl{
    private static final Map<UUID, Phrase> PHRASE_REPOSITORY = new ConcurrentHashMap<>();

    public void save(Phrase phrase) {
        enrichPhrase(phrase);
        PHRASE_REPOSITORY.put(UUID.randomUUID(), phrase);
    }

    public Phrase show() {
        if (PHRASE_REPOSITORY.isEmpty()) {
            return new Phrase(UUID.randomUUID(), "Sorry, but I don't have any phrases. Please save a phrase");
        }
        int randomIndex = new Random().nextInt(PHRASE_REPOSITORY.size());
        return PHRASE_REPOSITORY.values().stream().skip(randomIndex).findFirst().orElse(null);
    }

    private void enrichPhrase(Phrase phrase) {
        phrase.setId(UUID.randomUUID());
    }
}
