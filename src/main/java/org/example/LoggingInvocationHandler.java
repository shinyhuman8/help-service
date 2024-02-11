package org.example;

import org.example.annotations.GetMapping;
import org.example.annotations.Logged;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler implements InvocationHandler {
    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Logged.class)) {
            System.out.println("start " + method.getName());
            final var result = method.invoke(target, args);
            System.out.println("finished " + method.getName());
            return result;
        } else {
            return method.invoke(target, args);
        }
    }
}
