package com.black.monkey.my.election.core.infraestructure;



import com.black.monkey.my.election.core.event.BaseEvent;

import java.util.List;

public interface EventStore {

    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);

    List<BaseEvent> getEvents(String aggregateId);
}
