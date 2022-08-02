package com.black.monkey.my.election.cmd.domain;

import com.black.monkey.my.election.cmd.api.command.OpenCrvCommand;
import com.black.monkey.my.election.commons.event.CrvClosedEvent;
import com.black.monkey.my.election.commons.event.CrvOpenedEvent;
import com.black.monkey.my.election.commons.event.NoteAddedEvent;
import com.black.monkey.my.election.commons.event.VoteRegisteredEvent;
import com.black.monkey.my.election.commons.event.VoteUnRegisteredEvent;
import com.black.monkey.my.election.core.domain.AggregateRoot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CrvAggregate extends AggregateRoot {

    private boolean opened;
    private Set<VoteVO> voteList; // FIXME I guess we can remove it !!
    private List<NoteVO> noteList; // FIXME I guess we can remove it !!

    public CrvAggregate(OpenCrvCommand openCrvCommand) {
       open(openCrvCommand.getId());
    }

    public void apply(CrvOpenedEvent event) {
        this.id = event.getId();
        this.opened = true;
        this.voteList = new HashSet<>();
        this.noteList = new LinkedList<>();
    }

    public void close(String closeNote) {
        opened();

        CrvClosedEvent closedEvent = new CrvClosedEvent();
        closedEvent.setId(this.id);

        if (StringUtils.isNotEmpty(closeNote)) {
            addNote(NoteVO.builder()
                    .note(closeNote)
                    .build());
        }

        raiseEvent(closedEvent);
    }

    public void apply(CrvClosedEvent event) {
        this.id = event.getId();
        this.opened = false;
    }

    public void registerVote(VoteVO vote) {

        opened();

        VoteRegisteredEvent event = new VoteRegisteredEvent();
        event.setId(this.getId());
        event.setVote(vote);
        raiseEvent(event);
    }

    public void apply(VoteRegisteredEvent event) {
        this.id = event.getId();
        event.getVote().setRegisterBy(event.getEmail());
        voteList.add(event.getVote());
    }

    public void open(String id) {
        CrvOpenedEvent event = new CrvOpenedEvent();
        event.setId(id);
        raiseEvent(event);
    }

    public void unRegisterVote(VoteVO vote) {
        VoteUnRegisteredEvent event = new VoteUnRegisteredEvent();
        event.setId(this.getId());
        event.setVote(vote);
        raiseEvent(event);
    }

    public void apply(VoteUnRegisteredEvent event) {
        this.id = event.getId();
        voteList.remove(event.getVote());
    }

    public void addNote(NoteVO note) {
        NoteAddedEvent event = new NoteAddedEvent();
        event.setId(this.getId());
        event.setNote(note);
        raiseEvent(event);
    }

    public void apply(NoteAddedEvent event) {
        this.id = event.getId();
        event.getNote().setRegisterBy(event.getEmail());
        noteList.add(event.getNote());
    }

    private void opened() {
        if (!this.opened) {
            throw new IllegalStateException("CRV esta cerrado, no se pueden registrar mas votos");
        }
    }

}
