package org.example.dispatcher.impl;

import org.example.annotations.GetMapping;
import org.example.context.ApplicationContext;
import org.example.dispatcher.HttpBody;
import org.example.dispatcher.RequestGenerator;

import java.lang.reflect.Method;
import java.util.List;

public class GetRequestGenerator implements RequestGenerator {
    @Override
    public void handle(List<HttpBody> httpBodies, ApplicationContext applicationContext) {
        for (HttpBody httpBody : httpBodies) {
            String path = httpBody.getRequest().getRequestURI();
            Method handlerMethod = applicationContext.findGetHandler(path);

            executeRequest(httpBody, handlerMethod);
        }
    }

    @Override
    public Class<?> getType() {
        return GetMapping.class;
    }
}
