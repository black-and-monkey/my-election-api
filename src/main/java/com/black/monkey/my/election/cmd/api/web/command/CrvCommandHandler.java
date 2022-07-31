package com.black.monkey.my.election.cmd.api.web.command;

import com.black.monkey.my.election.cmd.domain.CrvAggregate;
import com.black.monkey.my.election.core.exceptions.CrvDoesntExistException;
import com.black.monkey.my.election.core.handlers.EventSourcingHandler;
import com.black.monkey.my.election.query.domain.Crv;
import com.black.monkey.my.election.query.repository.CrvRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.FormattedMessage;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrvCommandHandler implements CommandHandler {

    private final EventSourcingHandler<CrvAggregate> eventSourcingHandler;
    private final CrvRepository crvRepository;

    @Override
    public void handler(OpenCrvCommand command) {

        Optional<Crv> optionalCrv = crvRepository.findById(command.getId());

        if (optionalCrv.isEmpty()) {
            throw new CrvDoesntExistException(MessageFormat.format("crv {0}",command.getId()));
        }

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
