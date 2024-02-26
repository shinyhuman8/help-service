package org.example.configurations;

import org.example.repositories.MotivationRepository;

import org.example.services.PhraseService;
import org.example.services.Producer;
import org.example.services.impl.PhraseServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@ConditionalOnProperty(name = "kafka.help-service.enabled",havingValue = "true")
public class AppConfig {
    @Bean
    @Primary
    public PhraseService phraseService(MotivationRepository motivationRepository) {
        return new PhraseServiceImpl(motivationRepository);
    }
}
