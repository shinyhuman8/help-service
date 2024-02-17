package org.example.dispatcher;

import org.example.context.ApplicationContext;
import java.lang.reflect.Method;
import java.util.List;

public interface RequestGenerator {
    void handle(List<HttpBody> httpBodies, ApplicationContext applicationContext);
    Class<?> getType();


}
