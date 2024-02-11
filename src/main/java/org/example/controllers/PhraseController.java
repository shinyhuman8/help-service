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

import java.io.IOException;

@Controller
public class PhraseController {
    @Autowired
    private MotivationRepository motivationRepository;

    public void setMotivationRepository(MotivationRepository motivationRepository) {
        this.motivationRepository = motivationRepository;
    }

    @GetMapping("/help-service/v1/support/get")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        Phrase phrase = motivationRepository.show();
        PhraseDTO phraseDTO = new PhraseDTO(phrase.getPhrase());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(phraseDTO);

        response.getWriter().write(jsonString);
    }

    @PostMapping("/help-service/v1/support/post")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PhraseDTO receivedPhraseDTO = objectMapper.readValue(request.getReader(), PhraseDTO.class);

        if (receivedPhraseDTO.getPhrase() == null || receivedPhraseDTO.getPhrase().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
            response.getWriter().write("Phrase should not be empty");
        } else if (!request.getContentType().equals("text/plain")) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            response.getWriter().write("Phrase should be a text");
        } else {
            motivationRepository.save(convertToPhrase(receivedPhraseDTO));
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Phrase successfully saved");
        }
    }

    private Phrase convertToPhrase(PhraseDTO phraseDTO) {
        Phrase phrase = new Phrase();
        phrase.setPhrase(phraseDTO.getPhrase());
        return phrase;
    }
}
