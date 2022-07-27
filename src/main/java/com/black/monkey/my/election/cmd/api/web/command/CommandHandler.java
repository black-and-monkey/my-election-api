package com.black.monkey.my.election.cmd.api.web.command;

public interface CommandHandler {

    void handler(OpenCrvCommand command);
    void handler(CloseCrvCommand command);

}
