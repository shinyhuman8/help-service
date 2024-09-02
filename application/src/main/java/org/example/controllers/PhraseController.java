package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.DTO.PhraseDTO;
import org.example.models.Phrase;
import org.example.services.PhraseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/help-service/v1/support")
public class PhraseController {




    private final PhraseService phraseService;


    @GetMapping("/get")
    public ResponseEntity<PhraseDTO> getPhrase() {
        Phrase phrase = phraseService.showAnyPhrase();
        PhraseDTO phraseDTO = new PhraseDTO(phrase.getPhrase());
        return ResponseEntity.status(HttpStatus.OK).body(phraseDTO);
    }

    @PostMapping("/post")
    public ResponseEntity savePhrase(@RequestBody PhraseDTO phraseDTO) {
        phraseService.savePhrase(phraseDTO);
        return new ResponseEntity(phraseDTO.getPhrase(),HttpStatus.CREATED);
    }
}
