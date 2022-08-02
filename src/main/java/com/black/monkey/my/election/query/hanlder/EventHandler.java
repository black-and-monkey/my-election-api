package com.black.monkey.my.election.query.hanlder;

import com.black.monkey.my.election.commons.event.CrvClosedEvent;
import com.black.monkey.my.election.commons.event.CrvOpenedEvent;
import com.black.monkey.my.election.commons.event.NoteAddedEvent;
import com.black.monkey.my.election.commons.event.VoteRegisteredEvent;
import com.black.monkey.my.election.commons.event.VoteUnRegisteredEvent;

public interface EventHandler {
    void handler(CrvOpenedEvent event);
    void handler(CrvClosedEvent event);
    void handler(VoteRegisteredEvent event);
    void handler(VoteUnRegisteredEvent event);

    void handler(NoteAddedEvent noteAddedEvent);
}
