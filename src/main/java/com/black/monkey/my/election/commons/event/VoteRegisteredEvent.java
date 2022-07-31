package com.black.monkey.my.election.commons.event;

import com.black.monkey.my.election.cmd.domain.Vote;
import com.black.monkey.my.election.core.event.BaseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@ToString

public class VoteRegisteredEvent extends BaseEvent {

    private Vote vote;
}
