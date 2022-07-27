package com.black.monkey.my.election.core.command;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand>{
    void handle(T command);
}
