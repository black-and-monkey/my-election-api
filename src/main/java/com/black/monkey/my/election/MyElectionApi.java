package com.black.monkey.my.election;


import com.black.monkey.my.election.cmd.api.web.command.CloseCrvCommand;
import com.black.monkey.my.election.cmd.api.web.command.CommandHandler;
import com.black.monkey.my.election.cmd.api.web.command.OpenCrvCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
import com.black.monkey.my.election.commons.event.CrvClosedEvent;
import com.black.monkey.my.election.commons.event.CrvOpenedEvent;
import com.black.monkey.my.election.query.hanlder.EventHandler;
import com.black.monkey.my.election.query.infraestructure.EventDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MyElectionApi {

    public static void main(String[] args) {
        SpringApplication.run(MyElectionApi.class, args);
    }

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private CommandHandler commandHandler;

    @Autowired
    private EventDispatcher eventDispatcher;

    @Autowired
    private EventHandler eventHandler;

    @PostConstruct
    public void registerHandler() {
        commandDispatcher.registerHandler(OpenCrvCommand.class, commandHandler::handler);
        commandDispatcher.registerHandler(CloseCrvCommand.class, commandHandler::handler);

        eventDispatcher.registerHandler(CrvOpenedEvent.class, eventHandler::handler);
        eventDispatcher.registerHandler(CrvClosedEvent.class, eventHandler::handler);

    }

}
