package com.black.monkey.my.election.cmd.api.web.command;

import com.black.monkey.my.election.cmd.domain.CrvAggregate;
import com.black.monkey.my.election.core.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrvCommandHandler implements CommandHandler {

    private final EventSourcingHandler<CrvAggregate> eventSourcingHandler;

    @Override
    public void handler(OpenCrvCommand command) {
        // TODO check the user is allowed to open that table

        CrvAggregate aggregate = new CrvAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handler(CloseCrvCommand command) {
        CrvAggregate aggregateRoot = eventSourcingHandler.getById(command.getId());
        aggregateRoot.close();
        eventSourcingHandler.save(aggregateRoot);
    }
}
