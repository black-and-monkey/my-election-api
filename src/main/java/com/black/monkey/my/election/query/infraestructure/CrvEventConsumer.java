package com.black.monkey.my.election.query.infraestructure;

import com.black.monkey.my.election.core.event.MyApplicationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrvEventConsumer implements ApplicationListener<MyApplicationEvent> {

    private final EventDispatcher eventDispatcher;

    @Override
    public void onApplicationEvent(MyApplicationEvent event) {
        eventDispatcher.on(event.getEvent());
    }
}
