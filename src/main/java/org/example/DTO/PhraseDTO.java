package org.example.DTO;

public class PhraseDTO {
    private String phrase;

    public PhraseDTO() {
    }

    public PhraseDTO(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public String toString() {
        return "PhraseDTO{" +
                "phrase='" + phrase + '\'' +
                '}';
    }
}
