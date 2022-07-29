package com.black.monkey.my.election.core.domain;

import com.black.monkey.my.election.commons.helper.TokenHelper;
import com.black.monkey.my.election.core.event.BaseEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Setter
@Getter
public class AggregateRoot {

    protected String id;

    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();

    public List<BaseEvent> getUncommittedChanges() {
        return changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(BaseEvent event, Boolean isNewEvent) {
        try {
            Method method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this,event);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event) {
        event.setSub(TokenHelper.decodeToken().get("sub").toString());
        event.setEmail(TokenHelper.decodeToken().get("email").toString());
        applyChange(event, true);
    }

    public void replayEvent(Iterable<BaseEvent> events) {
        events.forEach( event -> applyChange(event, false));
    }


}
