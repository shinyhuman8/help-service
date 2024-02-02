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
        if (PHRASE_REPOSITORY.isEmpty())
            return "Sorry, but i don't have any phrases.\n" +
                    "Please save phrase";
        Random random = new Random();
        int phrase_id = random.nextInt(1, PHRASE_COUNT+1);
        return PHRASE_REPOSITORY.get(phrase_id);
    }

    public static int getPhraseCount() {
        return PHRASE_COUNT;
    }
}
