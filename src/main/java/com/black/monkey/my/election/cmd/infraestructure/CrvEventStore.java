package com.black.monkey.my.election.cmd.infraestructure;


import com.black.monkey.my.election.cmd.domain.CrvAggregate;
import com.black.monkey.my.election.cmd.domain.EventModel;
import com.black.monkey.my.election.cmd.domain.EventStoreRepository;
import com.black.monkey.my.election.core.event.BaseEvent;
import com.black.monkey.my.election.core.exceptions.AggregateNotFoundException;
import com.black.monkey.my.election.core.infraestructure.EventStore;
import com.black.monkey.my.election.core.producers.EventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrvEventStore implements EventStore {

    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;
    private final ObjectMapper mapper;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size()-1).getVersion() != expectedVersion) {
            throw new RuntimeException("version is not correct");
        }

        int version = expectedVersion;

        for (BaseEvent event: events) {
            version++;
            event.setVersion(version);

            EventModel persistedEvent = eventStoreRepository.save(EventModel.builder()
                    .timestamp(LocalDateTime.now(Clock.systemUTC()))
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(CrvAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .eventDataJson(object2json(event))
                    .build());

            if (persistedEvent.getId()!=null) {
                eventProducer.producer(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        List<EventModel> eventModels = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventModels == null || eventModels.isEmpty()) {
            throw new AggregateNotFoundException(MessageFormat.format("Incorrect account id provider: {0}",aggregateId));
        }


        return eventModels.stream().map( x -> x.getEventData(mapper)).collect(Collectors.toList());
    }

    private String object2json(Object object) {

        try {
           return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("couldn't covert to json");
        }
    }
}
