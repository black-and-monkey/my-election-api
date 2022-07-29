package com.black.monkey.my.election.cmd.domain;

import com.black.monkey.my.election.cmd.api.web.command.OpenCrvCommand;
import com.black.monkey.my.election.commons.event.CrvClosedEvent;
import com.black.monkey.my.election.commons.event.CrvOpenedEvent;
import com.black.monkey.my.election.core.domain.AggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Clock;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CrvAggregate extends AggregateRoot {

    private boolean opened;

    public CrvAggregate(OpenCrvCommand openCrvCommand) {
        CrvOpenedEvent event = new CrvOpenedEvent();
        event.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
        event.setId(openCrvCommand.getId());
        raiseEvent(event);
    }

    public void apply(CrvOpenedEvent event) {
        this.id = event.getId();
        this.opened = true;
    }

    public void close() {
        if (!this.opened) {
            throw new IllegalStateException("The crv has already been closed!");
        }

        CrvClosedEvent event = new CrvClosedEvent();
        event.setTimestamp(LocalDateTime.now(Clock.systemUTC()));
        event.setId(this.id);
        raiseEvent(event);
    }

    public void apply(CrvClosedEvent event) {
        this.id = event.getId();
        this.opened = false;
    }
}
