package org.example.context;

import org.example.LoggingInvocationHandler;
import org.example.annotations.Configuration;
import org.example.annotations.Controller;
import org.example.annotations.GetMapping;
import org.example.annotations.PostMapping;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class ApplicationContext {
    private final Reflections reflection;
    private final Map<Class<?>, Object> INSTANCE_BY_CLASS;

    public ApplicationContext(String packageToScan) {
        this.INSTANCE_BY_CLASS = new HashMap<>();
        this.reflection = new Reflections(packageToScan);
        init();
    }

    public ApplicationContext() {
        this("org.example");
    }

    private void init() {
        reflection.getTypesAnnotatedWith(Configuration.class).stream()
                .map(this::createInstanceByClass)
                .forEach(configInstance -> getMethodsWithoutObjectsMethods(configInstance)
                        .forEach(method -> initBeanByBeanMethod(method, configInstance)));
    }


    public <T> T getInstance(Class<T> clazz) {
        return (T) this.INSTANCE_BY_CLASS.get(clazz);
    }

    private Stream<Method> getMethodsWithoutObjectsMethods(Object instance) {
        return Arrays.stream(instance.getClass().getMethods()).filter(method -> !method.getDeclaringClass().equals(Object.class));
    }

    public <T> T createInstanceByClass(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void initBeanByBeanMethod(Method beanMethod, Object configInstance) {
        Class<?> returnType = beanMethod.getReturnType();
        try {
            final var instance = wrapWithLoggingProxy(beanMethod.invoke(configInstance));
            INSTANCE_BY_CLASS.put(returnType, instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Method findGetHandler(String path) {
        return findHandler(path, GetMapping.class);
    }

    public Method findPostHandler(String path) {
        return findHandler(path, PostMapping.class);
    }

    private Method findHandler(String path, Class<? extends Annotation> annotationType) {
        Set<Class<?>> controllers = reflection.getTypesAnnotatedWith(Controller.class);
        for (Class<?> controller : controllers) {
            for (Method method : controller.getMethods()) {
                if (method.isAnnotationPresent(annotationType)) {
                    String methodPath = null;
                    if (annotationType.equals(GetMapping.class)) {
                        methodPath = method.getAnnotation(GetMapping.class).value();
                    } else if (annotationType.equals(PostMapping.class)) {
                        methodPath = method.getAnnotation(PostMapping.class).value();
                    }
                    if (path.equals(methodPath)) {
                        return method;
                    }
                }
            }
        }
        return null;
    }
    private Object wrapWithLoggingProxy(Object object){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new LoggingInvocationHandler(object));

    }
}