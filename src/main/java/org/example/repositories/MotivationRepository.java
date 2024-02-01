package org.example.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MotivationRepository {
    private static final Map<Integer, String> PHRASE_REPOSITORY = new HashMap<>();

    private static int PHRASE_COUNT;

    public void save(String phrase) {
        PHRASE_REPOSITORY.put(++PHRASE_COUNT, phrase);
        System.out.println("Phrase " + phrase + " successfully save");
    }

    public String show() {
        Random random = new Random();
        if (PHRASE_REPOSITORY.isEmpty())
            return "Sorry, but i don't have any phrases.\n" +
                    "Please save phrase";
        int phrase_id = random.nextInt(PHRASE_REPOSITORY.size());
        return PHRASE_REPOSITORY.get(phrase_id);
    }
}
