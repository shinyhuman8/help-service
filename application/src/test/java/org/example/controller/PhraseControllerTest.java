package org.example.controller;

import org.example.controllers.PhraseController;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(PhraseController.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ControllerTestConfig.class})
public class PhraseControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    MotivationRepository repository;

    @Test
    void getRequestWithStatusOk() throws Exception {
        Mockito.when(repository.show()).thenReturn(new Phrase("Any phrase"));

        mvc.perform(MockMvcRequestBuilders.get("/help-service/v1/support/get"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phrase").value("Any phrase"));
    }


    @Test
    void postRequestWithStatusSaved() throws Exception {
        Mockito.doNothing().when(repository).save(Mockito.any());
        mvc.perform((MockMvcRequestBuilders.post("/help-service/v1/support/post"))
                        .contentType("application/json")
                        .content("""
                                 {
                                 "phrase" : "Any phrase"
                                 }
                                """))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }
}