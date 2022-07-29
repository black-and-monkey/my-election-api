package com.black.monkey.my.election.query.infraestructure;


import com.black.monkey.my.election.core.event.BaseEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CrvEventDispatcher implements EventDispatcher {

    private final Map<Class<? extends BaseEvent>, List<EventHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseEvent> void registerHandler(Class<T> type, EventHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void on(BaseEvent event) {
        List<EventHandlerMethod> handler = routes.get(event.getClass());

        if (handler == null || handler.size() == 0) {
            throw new RuntimeException("no event handler was register");
        }

        if (handler.size() > 1) {
            throw new RuntimeException("cannot send event to more than one handler !");
        }

        handler.get(0).handle(event);
    }
}
