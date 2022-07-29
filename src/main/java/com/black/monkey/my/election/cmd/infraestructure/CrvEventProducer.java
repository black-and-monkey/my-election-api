package com.black.monkey.my.election.cmd.infraestructure;

import com.black.monkey.my.election.core.event.BaseEvent;
import com.black.monkey.my.election.core.event.MyApplicationEvent;
import com.black.monkey.my.election.core.producers.EventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrvEventProducer implements EventProducer {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void producer(String topic, BaseEvent event) {
        log.debug("Publishing custom event: {}", event);
        applicationEventPublisher.publishEvent(new MyApplicationEvent(this,event));
    }
}
