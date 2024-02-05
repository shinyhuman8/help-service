package org.example.repositories;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MotivationRepository {
    private static final ConcurrentMap<UUID, String> PHRASE_REPOSITORY = new ConcurrentHashMap<>();

    public void save(String phrase) {
        PHRASE_REPOSITORY.put(UUID.randomUUID(), phrase);
        System.out.println("Phrase " + phrase + " successfully save");
    }

    public String show() {
        if (PHRASE_REPOSITORY.isEmpty())
            return "Sorry, but i don't have any phrases.\n" +
                    "Please save phrase";
        int randomIndex = new Random().nextInt(PHRASE_REPOSITORY.size());
        return PHRASE_REPOSITORY.values().stream()
                .skip(randomIndex)
                .findFirst()
                .orElse(null);
    }
}
