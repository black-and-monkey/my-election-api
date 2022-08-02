package com.black.monkey.my.election.commons.event;

import com.black.monkey.my.election.cmd.domain.VoteVO;
import com.black.monkey.my.election.core.event.BaseEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString

public class VoteRegisteredEvent extends BaseEvent {

    private VoteVO vote;
}
