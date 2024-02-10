package org.example.configuration;

import org.example.annotations.Bean;
import org.example.annotations.Configuration;
import org.example.controllers.PhraseController;
import org.example.dispatcher.DispatcherServlet;
import org.example.dispatcher.RequestGenerator;
import org.example.dispatcher.impl.GetRequestGenerator;
import org.example.dispatcher.impl.PostRequestGenerator;
import org.example.repositories.MotivationRepository;
import org.example.repositories.MotivationRepositoryImpl;

@Configuration
public class HelpServiceAppConfig {

    @Bean
    public MotivationRepository motivationRepository() {
        return new MotivationRepositoryImpl();
    }

    @Bean
    public RequestGenerator getRequestStrategy() {
        return new GetRequestGenerator();
    }

    @Bean
    public RequestGenerator postRequestStrategy() {
        return new PostRequestGenerator();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

}
