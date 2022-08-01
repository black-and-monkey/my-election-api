package com.black.monkey.my.election.cmd.domain;

import com.black.monkey.my.election.cmd.api.command.OpenCrvCommand;
import com.black.monkey.my.election.commons.event.CrvClosedEvent;
import com.black.monkey.my.election.commons.event.CrvOpenedEvent;
import com.black.monkey.my.election.commons.event.VoteRegisteredEvent;
import com.black.monkey.my.election.core.domain.AggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.MessageFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CrvAggregate extends AggregateRoot {

    private boolean opened;
    private Set<Vote> voteList;

    public CrvAggregate(OpenCrvCommand openCrvCommand) {
       open(openCrvCommand.getId());
    }

    public void apply(CrvOpenedEvent event) {
        this.id = event.getId();
        this.opened = true;
        this.voteList = new HashSet<>();
    }

    public void close() {
        opened();

        CrvClosedEvent event = new CrvClosedEvent();
        event.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
        event.setId(this.id);
        raiseEvent(event);
    }

    public void apply(CrvClosedEvent event) {
        this.id = event.getId();
        this.opened = false;
    }

    public void registerVote(Vote vote) {

        opened();

        VoteRegisteredEvent event = new VoteRegisteredEvent();
        event.setId(this.getId());
        event.setVote(vote);
        raiseEvent(event);
    }

    public void apply(VoteRegisteredEvent event) {
        this.id = event.getId();
        voteList.add(event.getVote());
    }

    private void opened() {
        if (!this.opened) {
            throw new IllegalStateException(MessageFormat.format("CRV {0} esta cerrado, no se pueden registrar mas votos",this.id));
        }
    }


    public void open(String id) {
        CrvOpenedEvent event = new CrvOpenedEvent();
        event.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
        event.setId(id);
        raiseEvent(event);
    }
}
