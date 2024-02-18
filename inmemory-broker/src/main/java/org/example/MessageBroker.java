package org.example;

import java.util.List;

public interface MessageBroker {
    void sendMessage(String message);

    List<String> consumeMessages();
}
