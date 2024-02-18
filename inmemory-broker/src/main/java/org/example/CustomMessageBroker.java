package org.example;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
@Service
public class CustomMessageBroker implements MessageBroker {
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    @Override
    public void sendMessage(String message) {
        queue.offer(message);
    }
    @Override
    public List<String> consumeMessages(){
        List<String> consumerMessages= new ArrayList<>();
        for (int i = 0; i < queue.size(); i++) {
            consumerMessages.add(queue.poll());
        }
        return consumerMessages;
    }
}
