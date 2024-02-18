package org.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SubscriberBeanPostProcessor implements BeanPostProcessor {
    private final CustomMessageBroker customMessageBroker;
    private final Map<Object, Method> registerSubscriber = new HashMap<>();

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
                    Thread.sleep(20000); // 500 миллисекунд задержка
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted", e);
                }
            }
        }).start();
    }

    public void pollMessages() {
        registerSubscriber.forEach((topicName, listeners) -> {
            List<String> messages = customMessageBroker.consumeMessages();
            if (!messages.isEmpty()) {
                registerSubscriber.forEach((bean, method) -> invokeListenerMethod(bean, method, messages));
            }
        });
    }

    private void invokeListenerMethod(Object bean, Method method, List<String> messages) {
        for (String message : messages) {
            try {
                method.setAccessible(true);
                method.invoke(bean, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
