package org.example.controllers;

import org.example.DTO.PhraseDTO;
import org.example.services.PhraseService;
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
        return new PhraseDTO(phraseService.showAnyPhrase().getPhrase());
    }

    @PostMapping("/post")
    public String savePhrase(@RequestBody PhraseDTO phraseDTO) {
        return phraseService.savePhrase(phraseDTO);
    }
}
