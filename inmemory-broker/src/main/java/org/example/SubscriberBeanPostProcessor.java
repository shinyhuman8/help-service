package org.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SubscriberBeanPostProcessor implements BeanPostProcessor {
    private final CustomMessageBroker customMessageBroker;
    private final Map<Object, Method> registerSubscriber = new ConcurrentHashMap<>();

    public SubscriberBeanPostProcessor(CustomMessageBroker customMessageBroker) {
        this.customMessageBroker = customMessageBroker;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscriber.class)) {
                registerSubscriber.put(bean, method);
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        startPolling();
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    public void startPolling() {
        new Thread(() -> {
            while (true) {
                try {
                    pollMessages();
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted", e);
                }
            }
        }).start();
    }

    public void pollMessages() {
        registerSubscriber.forEach((topicName, listeners) -> {
            List<Object> messages = customMessageBroker.consumeMessages();
            if (!messages.isEmpty()) {
                registerSubscriber.forEach((bean, method) -> invokeSubscriberMethod(bean, method, messages));
            }
        });
    }

    private void invokeSubscriberMethod(Object bean, Method method, List<Object> messages) {
        for (Object message : messages) {
            try {
                method.setAccessible(true);
                method.invoke(bean, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
