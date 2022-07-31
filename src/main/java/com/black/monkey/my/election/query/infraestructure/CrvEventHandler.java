package com.black.monkey.my.election.query.infraestructure;

import com.black.monkey.my.election.commons.event.CrvClosedEvent;
import com.black.monkey.my.election.commons.event.CrvOpenedEvent;
import com.black.monkey.my.election.commons.event.VoteRegisteredEvent;
import com.black.monkey.my.election.core.event.BaseEvent;
import com.black.monkey.my.election.query.domain.Crv;
import com.black.monkey.my.election.query.domain.MyUser;
import com.black.monkey.my.election.query.domain.VoteRegistration;
import com.black.monkey.my.election.query.hanlder.EventHandler;
import com.black.monkey.my.election.query.repository.CrvRepository;
import com.black.monkey.my.election.query.repository.MyUserRepository;
import com.black.monkey.my.election.query.repository.VoteRegistrationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrvEventHandler implements EventHandler {

    private final MyUserRepository myUserRepository;
    private final CrvRepository crvRepository;
    private final VoteRegistrationRepository voteRegistrationRepository;

    @Override
    public void handler(CrvOpenedEvent event) {
        log.debug("handler {}",event);
        Optional<Crv> optionalCrv = crvRepository.findById(event.getId());
        if (optionalCrv.isPresent()) {
            optionalCrv.get().setOpened(true);
            optionalCrv.get().setOpenedAt(LocalDateTime.now(Clock.systemUTC()));
            optionalCrv.get().setOpenedBy(getUser(event).get());
            crvRepository.save(optionalCrv.get());
        } else {
            log.warn("couldn't find CRV, {}", event.getId());
        }
    }

    @Override
    public void handler(CrvClosedEvent event) {

        log.debug("handler {}",event);
        Optional<Crv> optionalCrv = crvRepository.findById(event.getId());
        if (optionalCrv.isPresent()) {
            optionalCrv.get().setOpened(true);
            optionalCrv.get().setClosedAt(LocalDateTime.now(Clock.systemUTC()));
            optionalCrv.get().setClosedBy(getUser(event).get());
            crvRepository.save(optionalCrv.get());
        } else {
            log.warn("couldn't find CRV, {}", event.getId());
        }
    }

    @Override
    public void handler(VoteRegisteredEvent event) {
        log.debug("handler {}",event);

        Optional<Crv> optionalCrv = crvRepository.findById(event.getId());
        if (optionalCrv.isPresent()) {
            voteRegistrationRepository.save(VoteRegistration.builder()
                            .crv(optionalCrv.get())
                            .ci(event.getVote().getCi())
                            .dob(event.getVote().getDob())
                            .fullName(event.getVote().getFullName())
                            .timestamp(LocalDateTime.now(Clock.systemUTC()))
                            .registeredBy(getUser(event).orElse(null))
                    .build());
        } else {
            log.warn("couldn't find CRV, {}, vote NOT registered !!", event.getId());
        }
    }

    private Optional<MyUser> getUser(BaseEvent event) {
        Optional<MyUser> optionalUser = myUserRepository.findById(event.getEmail());

        if (optionalUser.isEmpty()) {
            // saving a new user
            MyUser myUser = new MyUser();
            myUser.setEmail(event.getEmail());
            myUser.setSub(event.getSub());
            myUserRepository.save(myUser);
            return Optional.of(myUser);
        }

        return optionalUser;
    }
}
