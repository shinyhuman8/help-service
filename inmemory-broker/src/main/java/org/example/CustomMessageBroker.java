package org.example;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
@Service
public class CustomMessageBroker<T> implements MessageBroker<T> {
    private final BlockingQueue<T> queue = new LinkedBlockingQueue<>();

    @Override
    public void sendMessage(T message) {
        queue.offer(message);
    }
    @Override
    public T consumeMessages(){
        return queue.poll();
    }
}
