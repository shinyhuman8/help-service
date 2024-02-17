package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DTO.PhraseDTO;
import org.example.annotations.Autowired;
import org.example.annotations.Controller;
import org.example.annotations.GetMapping;
import org.example.annotations.PostMapping;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepository;
import org.example.services.PhraseService;

import java.io.IOException;

@Controller
public class PhraseController {
    @Autowired
    private PhraseService phraseService;
    @Autowired
    private ObjectMapper objectMapper;

    public void setPhraseService(PhraseService phraseService) {
        this.phraseService = phraseService;
    }
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/help-service/v1/support/get")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        Phrase phrase = phraseService.showAnyPhrase();
        PhraseDTO phraseDTO = new PhraseDTO(phrase.getPhrase());

        String jsonString = objectMapper.writeValueAsString(phraseDTO);

        response.getWriter().write(jsonString);
    }

    @PostMapping("/help-service/v1/support/post")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PhraseDTO receivedPhraseDTO = objectMapper.readValue(request.getReader(), PhraseDTO.class);

        phraseService.doResponse(receivedPhraseDTO, response);
    }
}
