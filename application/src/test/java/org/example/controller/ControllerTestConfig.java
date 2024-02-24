package org.example.controller;

import org.example.CustomMessageBroker;
import org.example.MessageBroker;
import org.example.SubscriberBeanPostProcessor;
import org.example.controllers.PhraseController;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepository;
import org.example.repositories.MotivationRepositoryImpl;
import org.example.services.PhraseService;
import org.example.services.PhraseServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ControllerTestConfig {
    @Bean
    public MotivationRepository motivationRepository() {
        return new MotivationRepositoryImpl();
    }

    @Bean
    public MessageBroker<Phrase> messageBroker() {
        return new CustomMessageBroker<>();
    }

    @Bean
    public PhraseService phraseService(MotivationRepository motivationRepository,
                                       MessageBroker<Phrase> sP){
        return new PhraseServiceImpl(motivationRepository, sP);
    }

    @Bean
    public PhraseController phraseController(PhraseService phraseService) {
        return new PhraseController(phraseService);
    }

    @Bean
    public SubscriberBeanPostProcessor postProcessor(MessageBroker messageBroker) {
        return new SubscriberBeanPostProcessor(messageBroker);
    }
}
