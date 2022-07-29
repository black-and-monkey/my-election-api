package com.black.monkey.my.election.query.hanlder;

import com.black.monkey.my.election.commons.event.CrvClosedEvent;
import com.black.monkey.my.election.commons.event.CrvOpenedEvent;

public interface EventHandler {
    void handler(CrvOpenedEvent event);
    void handler(CrvClosedEvent event);

}
