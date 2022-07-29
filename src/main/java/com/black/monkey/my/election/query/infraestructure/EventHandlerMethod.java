package com.black.monkey.my.election.query.infraestructure;

import com.black.monkey.my.election.core.event.BaseEvent;

@FunctionalInterface
public interface EventHandlerMethod<T extends BaseEvent>{
    void handle(T event);
}
