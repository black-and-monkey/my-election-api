package com.black.monkey.my.election.cmd.api.command;

import com.black.monkey.my.election.core.command.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VoteUnRegistrationCommand extends BaseCommand {

    private String ci;
}
