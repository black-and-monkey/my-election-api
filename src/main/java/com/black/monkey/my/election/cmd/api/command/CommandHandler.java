package com.black.monkey.my.election.cmd.api.command;

public interface CommandHandler {

    void handler(OpenCrvCommand command);
    void handler(CloseCrvCommand command);
    void handler(VoteRegistrationCommand command);
    void handler(VoteUnRegistrationCommand command);
    void handler(NoteAddCommand noteAddCommand);
}
