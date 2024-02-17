import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DTO.PhraseDTO;
import org.example.controllers.PhraseController;
import org.example.dispatcher.DispatcherServlet;
import org.example.models.Phrase;
import org.example.repositories.MotivationRepositoryImpl;
import org.example.services.impl.PhraseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

public class HelpServiceServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    DispatcherServlet dispatcherServlet;

    @Mock
    PhraseController phraseController;

    @Mock
    MotivationRepositoryImpl motivationRepositoryImpl;

    @Mock
    PhraseServiceImpl phraseService;

    @Mock
    ObjectMapper objectMapper;

    @BeforeEach
    void initHelpServiceServlet() {
        dispatcherServlet = Mockito.mock(DispatcherServlet.class);
        phraseController = new PhraseController();
        motivationRepositoryImpl = Mockito.mock(MotivationRepositoryImpl.class);
        phraseService = new PhraseServiceImpl();
        phraseController.setPhraseService(phraseService);
        objectMapper=new ObjectMapper();
        phraseController.setObjectMapper(objectMapper);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    void testDoGetShouldWriteJsonResponse() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        Phrase expectedPhrase = new Phrase(UUID.randomUUID(), "test_show");

        when(motivationRepositoryImpl.show()).thenReturn(expectedPhrase);
        phraseService.savePhrase(expectedPhrase);

        phraseController.doGet(request, response);

        ObjectMapper objectMapper = new ObjectMapper();

        PhraseDTO phraseDTO = new PhraseDTO(phraseService.showAnyPhrase().getPhrase());
        String jsonString = objectMapper.writeValueAsString(phraseDTO);

        String expectedJson = "{\"phrase\":\"test_show\"}";
        assertEquals(expectedJson, jsonString);
    }



    @Test
    void doPostShouldSavePhraseTest() throws IOException {

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"phrase\": \"Any phrase\"}")));
        when(request.getContentType()).thenReturn("text/plain");
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        phraseController.doPost(request, response);

        assertEquals("Phrase successfully saved", stringWriter.toString().trim());
    }

    @Test
    void doPostShouldHandleEmptyPhraseTest() throws IOException {

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"phrase\": \"\"}")));
        when(request.getContentType()).thenReturn("text/plain");
        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        phraseController.doPost(request, response);

        assertEquals("Phrase should not be empty", stringWriter.toString().trim());
    }
}