package org.example.dispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.annotations.GetMapping;
import org.example.annotations.PostMapping;
import org.example.context.ApplicationContext;
import org.example.dispatcher.impl.GetRequestGenerator;
import org.example.dispatcher.impl.PostRequestGenerator;
import org.example.repositories.MotivationRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {

    private ApplicationContext applicationContext;
    private final Map<Class<?>, RequestGenerator> handlers = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        this.applicationContext = new ApplicationContext();
    }

    public DispatcherServlet() {}

    public void addServletRequests(RequestGenerator generator) {
        this.handlers.put(generator.getType(), generator);
    }

    public void generateRequest(List<HttpBody> httpBodies, Class<?> type) {
        handlers.get(type).handle(httpBodies, applicationContext);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            String method = request.getMethod();
            Class<?> type = getTypeRequest(method);

            HttpBody httpBody = new HttpBody(request, response);
            generateRequest(Collections.singletonList(httpBody), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class<?> getTypeRequest(String method) {
        Class<?> type = null;

        if (method.equals("POST")) {
            type = PostMapping.class;
            addServletRequests(new PostRequestGenerator());

        } else if (method.equals("GET")) {
            type = GetMapping.class;
            addServletRequests(new GetRequestGenerator());
        }

        return type;
    }
}
