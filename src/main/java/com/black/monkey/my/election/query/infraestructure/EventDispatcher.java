package com.black.monkey.my.election.query.infraestructure;


import com.black.monkey.my.election.core.event.BaseEvent;

public interface EventDispatcher {

    <T extends BaseEvent> void registerHandler(Class<T> type, EventHandlerMethod<T> handler);

    void on(BaseEvent event);
}
