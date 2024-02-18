package org.example;

import java.util.List;

public interface MessageBroker<T> {
    void sendMessage(T message);

    List<Object> consumeMessages();
}
