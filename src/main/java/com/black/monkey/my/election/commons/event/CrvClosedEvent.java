package com.black.monkey.my.election.commons.event;

import com.black.monkey.my.election.core.event.BaseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class CrvClosedEvent extends BaseEvent {

    private LocalDateTime timestamp;
}
