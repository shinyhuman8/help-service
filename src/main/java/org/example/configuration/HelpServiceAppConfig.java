package org.example.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.annotations.Bean;
import org.example.annotations.Configuration;

import org.example.controllers.PhraseController;
import org.example.dispatcher.DispatcherServlet;
import org.example.dispatcher.RequestGenerator;
import org.example.dispatcher.impl.GetRequestGenerator;
import org.example.dispatcher.impl.PostRequestGenerator;
import org.example.repositories.MotivationRepository;
import org.example.repositories.MotivationRepositoryImpl;
import org.example.services.PhraseService;
import org.example.services.impl.PhraseServiceImpl;

@Configuration
public class HelpServiceAppConfig {

    public MotivationRepository motivationRepository() {
        return new MotivationRepositoryImpl();
    }

    public RequestGenerator getRequestStrategy() {
        return new GetRequestGenerator();
    }

    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    public PhraseController phraseController() {
        return new PhraseController();
    }

    public PhraseService phraseService() {
        return new PhraseServiceImpl();
    }
    public ObjectMapper objectMapper(){return new ObjectMapper();}
}
