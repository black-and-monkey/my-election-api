package com.black.monkey.my.election.cmd.infraestructure;

import com.black.monkey.my.election.cmd.domain.CrvAggregate;
import com.black.monkey.my.election.core.domain.AggregateRoot;
import com.black.monkey.my.election.core.event.BaseEvent;
import com.black.monkey.my.election.core.handlers.EventSourcingHandler;
import com.black.monkey.my.election.core.infraestructure.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrvEventSourcingHandler implements EventSourcingHandler<CrvAggregate> {

    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public CrvAggregate getById(String id) {
        CrvAggregate aggregate = new CrvAggregate();
        List<BaseEvent> events = eventStore.getEvents(id);
        if (events !=null && !events.isEmpty()) {
            aggregate.replayEvent(events);
            int lastVersion = events.stream().map( x -> x.getVersion()).max(Comparator.naturalOrder()).get();
            aggregate.setVersion(lastVersion);
        }
        return aggregate;
    }
}
