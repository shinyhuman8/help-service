package org.example.context;

import org.example.LoggingInvocationHandler;
import org.example.annotations.Autowired;
import org.example.annotations.Configuration;
import org.example.annotations.Controller;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
    private final Map<Class<?>, Object> BEAN_BY_CLASS;

    public ApplicationContext(String packageToScan) {
        this.BEAN_BY_CLASS = new HashMap<>();
        this.reflection = new Reflections(packageToScan);
        init();
    }

    public ApplicationContext() {
        this("org.example");
    }

    public Reflections getReflection() {
        return reflection;
    }

    private void init() {
        reflection.getTypesAnnotatedWith(Configuration.class).stream()
                .map(this::createBeanByClass)
                .forEach(configInstance -> getMethodsWithoutObjectsMethods(configInstance)
                        .forEach(method -> initBeanByBeanMethod(method, configInstance)));
    }

    public <T> T getBean(Class<T> clazz) {
        return (T) this.BEAN_BY_CLASS.get(clazz);
    }

    private Stream<Method> getMethodsWithoutObjectsMethods(Object instance) {
        return Arrays.stream(instance.getClass().getMethods()).filter(method -> !method.getDeclaringClass().equals(Object.class));
    }

    public <T> T createBeanByClass(Class<T> clazz) {
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
            BEAN_BY_CLASS.put(returnType, instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void injectFieldBean(Object controller) {
        for (Field field : controller.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                try {
                    field.set(controller, getBean(field.getType()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private Object wrapWithLoggingProxy(Object object) {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new LoggingInvocationHandler(object));

    }
}