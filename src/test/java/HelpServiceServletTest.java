import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.HelpServiceServlet;
import org.example.repositories.MotivationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class HelpServiceServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    PrintWriter writer;

    HelpServiceServlet servlet;
    MotivationRepository motivationRepository;

    @BeforeEach
    void initHelpServiceServlet() {
        servlet = new HelpServiceServlet();
        motivationRepository = new MotivationRepository();
    }

    @Test
    void doGetShouldWritePhraseToResponseTest() throws IOException {
        MockitoAnnotations.openMocks(this);
        MotivationRepository motivationRepository = mock(MotivationRepository.class);

        motivationRepository.save("test_show");

        when(response.getWriter()).thenReturn(writer);
        when(motivationRepository.show()).thenReturn("test_show");

        servlet.doGet(request, response);

        verify(writer).write(anyString());
        assertEquals(motivationRepository.show(), "test_show");
    }

    @Test
    void doPostShouldSavePhraseTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        BufferedReader reader = mock(BufferedReader.class);

        when(request.getReader()).thenReturn(reader);
        when(reader.readLine()).thenReturn("Any phrase");
        when(request.getContentType()).thenReturn("text/plain");

        assertEquals("Sorry, but i don't have any phrases.\nPlease save phrase",
                motivationRepository.show());

        servlet.doPost(request, response);

        assertEquals("Any phrase",
                motivationRepository.show());

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void doPostShouldHandleEmptyPhraseTest() throws IOException {
        MockitoAnnotations.openMocks(this);
        BufferedReader reader = mock(BufferedReader.class);

        when(request.getReader()).thenReturn(reader);
        when(reader.readLine()).thenReturn("");
        when(request.getContentType()).thenReturn("text/plain");
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
        verify(writer).write("Phrase should not be empty");
    }
}
