package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.DTO.PhraseDTO;
import org.example.MessageBroker;
import org.example.models.Phrase;
import org.example.services.PhraseServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/help-service/v1/support")
public class PhraseController {

    private final PhraseServiceImpl phraseService;
    private final MessageBroker messageBroker;

    @GetMapping("/get")
    public ResponseEntity<PhraseDTO> getPhrase() {
        Phrase phrase = phraseService.showAnyPhrase();
        PhraseDTO phraseDTO = new PhraseDTO(phrase.getPhrase());
        return ResponseEntity.ok(phraseDTO);
    }

    @PostMapping("/post")
    public ResponseEntity savePhrase(@RequestBody PhraseDTO phraseDTO) throws IOException {
        //phraseService.doResponse(phraseDTO);
        messageBroker.sendMessage(phraseDTO.getPhrase());
        return new ResponseEntity(phraseDTO.getPhrase(),HttpStatus.CREATED);
    }
}
