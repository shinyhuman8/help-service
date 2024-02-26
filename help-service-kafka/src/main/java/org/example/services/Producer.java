package org.example.services;

import org.example.DTO.PhraseDTO;

public interface Producer {
    String sendMessage(PhraseDTO phrase);
}
