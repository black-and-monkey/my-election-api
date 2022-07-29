package com.black.monkey.my.election.core.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class MyApplicationEvent extends ApplicationEvent {

    private BaseEvent event;

    public MyApplicationEvent(Object source, BaseEvent baseEvent) {
        super(source);
        this.event = baseEvent;
    }
}
