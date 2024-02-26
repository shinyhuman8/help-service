package org.example.controllers;

import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;
import org.example.services.PhraseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/help-service/v1/support")
public class PhraseController {
    private final PhraseService phraseService;


    public PhraseController(PhraseService phraseService) {
        this.phraseService = phraseService;
    }

    @GetMapping("/get")
    public PhraseDTO getPhrase() {
        Phrase phrase = phraseService.showAnyPhrase();
        PhraseDTO phraseDTO = new PhraseDTO(phrase.getPhrase());
        return phraseDTO;
    }

    @PostMapping("/post")
    public void savePhrase(@RequestBody PhraseDTO phraseDTO) {
        phraseService.savePhrase(phraseDTO);
    }
}
