package com.black.monkey.my.election.cmd.api.command;

import com.black.monkey.my.election.cmd.domain.CrvAggregate;
import com.black.monkey.my.election.cmd.domain.Vote;
import com.black.monkey.my.election.core.exceptions.CrvDoesntExistException;
import com.black.monkey.my.election.core.exceptions.PreviousVoteException;
import com.black.monkey.my.election.core.handlers.EventSourcingHandler;
import com.black.monkey.my.election.query.domain.Crv;
import com.black.monkey.my.election.query.domain.VoteRegistration;
import com.black.monkey.my.election.query.repository.CrvRepository;
import com.black.monkey.my.election.query.repository.VoteRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandHandlerCrv implements CommandHandler {

    private final EventSourcingHandler<CrvAggregate> eventSourcingHandler;
    private final CrvRepository crvRepository;
    private final VoteRegistrationRepository voteRegistrationRepository;

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

    @Override
    public void handler(VoteRegistrationCommand command) {

        // TODO check CI digits

        Optional<VoteRegistration> previousVote = voteRegistrationRepository.findByCi(command.getCi());
        if (previousVote.isPresent()) {
            throw new PreviousVoteException(MessageFormat.format("Ya existe voto para la CI, {0}",command.getCi()));
        }

        CrvAggregate aggregateRoot = eventSourcingHandler.getById(command.getId());
        aggregateRoot.registerVote(Vote.builder()
                        .ci(command.getCi())
                        .fullName(command.getFullName())
                        .dob(command.getDob())
                        .timestamp(LocalDateTime.now(Clock.systemUTC()))
                .build());
        eventSourcingHandler.save(aggregateRoot);
    }
}
