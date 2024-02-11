package org.example.dispatcher;

import org.example.context.ApplicationContext;
import java.lang.reflect.Method;
import java.util.List;

public interface RequestGenerator {
    void handle(List<HttpBody> httpBodies, ApplicationContext applicationContext);
    Class<?> getType();

    default void executeRequest(HttpBody httpBody, Method handlerMethod) {
        if (handlerMethod != null) {
            try {
                Object controller = handlerMethod
                        .getDeclaringClass()
                        .getDeclaredConstructor()
                        .newInstance();
                new ApplicationContext().injectFieldBean(controller);
                handlerMethod.invoke(controller, httpBody.getRequest(), httpBody.getResponse());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Обработчик не найден");
        }
    }
}
