package com.black.monkey.my.election.core.producers;


import com.black.monkey.my.election.core.event.BaseEvent;

public interface EventProducer {
    void producer(String topic, BaseEvent event);
}
