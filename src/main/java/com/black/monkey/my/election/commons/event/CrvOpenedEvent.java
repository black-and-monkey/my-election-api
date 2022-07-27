package com.black.monkey.my.election.commons.event;

import com.black.monkey.my.election.core.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CrvOpenedEvent extends BaseEvent {

    private LocalDateTime timestamp;
    private String by;

}
