package com.black.monkey.my.election.cmd.infraestructure;



import com.black.monkey.my.election.core.command.CommandHandlerMethod;
import com.black.monkey.my.election.core.command.BaseCommand;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CommandDispatcherCrv implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        List<CommandHandlerMethod> handler = routes.get(command.getClass());

        if (handler == null || handler.size() == 0) {
            throw new RuntimeException("no command handler was register");
        }

        if (handler.size() > 1) {
            throw new RuntimeException("cannot send command to more than one handler !");
        }

        handler.get(0).handle(command);
    }
}
