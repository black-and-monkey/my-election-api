package com.black.monkey.my.election.cmd.infraestructure;



import com.black.monkey.my.election.core.command.CommandHandlerMethod;
import com.black.monkey.my.election.core.command.BaseCommand;

public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    void send(BaseCommand command);
}
