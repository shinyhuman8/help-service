package org.example.repositories.impl;

import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MotivationRepositoryImpl implements MotivationRepository {
    private static final Map<UUID, Phrase> PHRASE_REPOSITORY = new ConcurrentHashMap<>();

    @Override
    public void save(Phrase phrase) {
        PHRASE_REPOSITORY.put(UUID.randomUUID(), phrase);
    }

    @Override
    public Phrase show() {
        if (PHRASE_REPOSITORY.isEmpty()) {
            return new Phrase(UUID.randomUUID(), "Sorry, but I don't have any phrases. Please save a phrase");
        }
        int randomIndex = new Random().nextInt(PHRASE_REPOSITORY.size());
        return PHRASE_REPOSITORY.values().stream().skip(randomIndex).findFirst().orElse(null);
    }

    @Override
    public boolean checkUniquePhrase(PhraseDTO phrase) {
        return PHRASE_REPOSITORY.values().stream().anyMatch(p -> phrase.getPhrase().equals(p.getPhrase()));
    }


}
