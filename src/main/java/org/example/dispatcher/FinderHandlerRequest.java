package org.example.dispatcher;

import org.example.annotations.Controller;
import org.example.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public interface FinderHandlerRequest {
    default Method findHandler(String path, Class<? extends Annotation> annotationType,
                               ApplicationContext context) {
        Set<Class<?>> controllers = context.getReflection().getTypesAnnotatedWith(Controller.class);
        for (Class<?> controller : controllers) {
            for (Method method : controller.getMethods()) {
                if (method.isAnnotationPresent(annotationType)) {
                    String methodPath = null;
                    try {
                        Method valueMethod = annotationType.getMethod("value");
                        methodPath = (String) valueMethod.invoke(method.getAnnotation(annotationType));
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    if (path.equals(methodPath)) {
                        return method;
                    }
                }
            }
        }
        return null;
    }
}
