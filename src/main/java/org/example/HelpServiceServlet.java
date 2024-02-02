package org.example;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.repositories.MotivationRepository;
import java.io.IOException;

public class HelpServiceServlet extends HttpServlet {
    private MotivationRepository motivationRepository = new MotivationRepository();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(motivationRepository.show());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newPhrase = request.getReader().readLine();
        if (newPhrase == null || newPhrase.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
            response.getWriter().write("Phrase should not be empty");
        } else if (!request.getContentType().equals("text/plain") ) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            response.getWriter().write("Phrase should be a text");
        } else {
            motivationRepository.save(newPhrase);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }
}
