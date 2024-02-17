package org.example.dispatcher.utils;

import org.example.context.ApplicationContext;
import org.example.dispatcher.HttpBody;

import java.lang.reflect.Method;

public class ExecutorRequest {
    public static void executeRequest(HttpBody httpBody, Method handlerMethod) {
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
