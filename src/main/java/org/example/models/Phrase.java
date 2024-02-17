package org.example.models;

import java.util.UUID;

public class Phrase {
    private UUID id;
    private String phrase;

    public Phrase() {
    }

    public Phrase(UUID id, String phrase) {
        this.id = id;
        this.phrase = phrase;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "id=" + id +
                ", phrase='" + phrase + '\'' +
                '}';
    }
}
