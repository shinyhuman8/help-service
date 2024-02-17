package org.example.dispatcher.impl;

import org.example.annotations.GetMapping;
import org.example.annotations.PostMapping;
import org.example.context.ApplicationContext;
import org.example.dispatcher.FinderHandlerRequest;
import org.example.dispatcher.HttpBody;
import org.example.dispatcher.RequestGenerator;
import org.example.dispatcher.utils.ExecutorRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class GetRequestGenerator implements  RequestGenerator, FinderHandlerRequest {
    @Override
    public void handle(List<HttpBody> httpBodies, ApplicationContext applicationContext) {
        for (HttpBody httpBody : httpBodies) {
            String path = httpBody.getRequest().getRequestURI();

            ExecutorRequest.executeRequest(httpBody, findHandler(path, this.getType(), applicationContext));
        }
    }

    @Override
    public Class<? extends Annotation> getType() {
        return GetMapping.class;
    }
}
