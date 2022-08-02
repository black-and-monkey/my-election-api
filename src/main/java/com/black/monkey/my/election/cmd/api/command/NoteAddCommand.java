package com.black.monkey.my.election.cmd.api.command;

import com.black.monkey.my.election.core.command.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class NoteAddCommand extends BaseCommand {

    private String note;
}
