package com.black.monkey.my.election.cmd.api.command;

import com.black.monkey.my.election.cmd.domain.CrvAggregate;
import com.black.monkey.my.election.cmd.domain.NoteVO;
import com.black.monkey.my.election.cmd.domain.VoteVO;
import com.black.monkey.my.election.commons.helper.UruguayanCiTool;
import com.black.monkey.my.election.core.exceptions.CrvDoesntExistException;
import com.black.monkey.my.election.core.exceptions.CrvIsOpenException;
import com.black.monkey.my.election.core.exceptions.InvalidDobException;
import com.black.monkey.my.election.core.exceptions.PreviousVoteException;
import com.black.monkey.my.election.core.handlers.EventSourcingHandler;
import com.black.monkey.my.election.query.domain.Crv;
import com.black.monkey.my.election.query.domain.VoteRegistration;
import com.black.monkey.my.election.query.repository.CrvRepository;
import com.black.monkey.my.election.query.repository.VoteRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Clock;
import java.time.LocalDate;
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

        CrvAggregate aggregate = eventSourcingHandler.getById(command.getId());

        if (StringUtils.isEmpty(aggregate.getId())) {
            aggregate = new CrvAggregate(command);
        } else if (!aggregate.isOpened()) {
            aggregate.open(command.getId());
        } else {
            throw new CrvIsOpenException(MessageFormat.format("crv {0} already opened",command.getId()));
        }

        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handler(CloseCrvCommand command) {
        CrvAggregate aggregateRoot = eventSourcingHandler.getById(command.getId());
        aggregateRoot.close(command.getNote());
        eventSourcingHandler.save(aggregateRoot);
    }

    @Override
    public void handler(VoteRegistrationCommand command) {

        UruguayanCiTool.check(command.getCi());

        if (command.getDob().isBefore(LocalDate.of(1991,11,6)) ||
                command.getDob().isAfter(LocalDate.of(2008,11,5))) {
            throw new InvalidDobException(MessageFormat.format("Fecha de nacimiento invalida: {0}, debe ser entre 6/11/1991 y 5/11/2008",command.getDob()));
        }

        Optional<VoteRegistration> previousVote = voteRegistrationRepository.findByCi(StringUtils.getDigits(command.getCi()));
        if (previousVote.isPresent()) {
            throw new PreviousVoteException(MessageFormat.format("Ya existe voto para la CI, {0}",command.getCi()));
        }

        CrvAggregate aggregateRoot = eventSourcingHandler.getById(command.getId());
        aggregateRoot.registerVote(VoteVO.builder()
                .ci(StringUtils.getDigits(command.getCi()))
                .fullName(command.getFullName())
                .dob(command.getDob())
                .timestamp(LocalDateTime.now(Clock.systemUTC()))
                .build());

        eventSourcingHandler.save(aggregateRoot);
    }

    @Override
    public void handler(VoteUnRegistrationCommand command) {
        Optional<VoteRegistration> previousVote = voteRegistrationRepository.findByCi(StringUtils.getDigits(command.getCi()));
        if (previousVote.isEmpty()) {
            throw new PreviousVoteException(MessageFormat.format("No existe voto para la CI, {0}", command.getCi()));
        }

        CrvAggregate aggregateRoot = eventSourcingHandler.getById(previousVote.get().getCrv().getId());

        aggregateRoot.unRegisterVote(VoteVO.builder()
                .timestamp(LocalDateTime.now(Clock.systemUTC()))
                .ci(StringUtils.getDigits(command.getCi()))
                .build());

        eventSourcingHandler.save(aggregateRoot);
    }

    @Override
    public void handler(NoteAddCommand command) {
        CrvAggregate aggregateRoot = eventSourcingHandler.getById(command.getId());

        aggregateRoot.addNote(NoteVO.builder()
                .note(command.getNote())
                .build());

        eventSourcingHandler.save(aggregateRoot);
    }
}
