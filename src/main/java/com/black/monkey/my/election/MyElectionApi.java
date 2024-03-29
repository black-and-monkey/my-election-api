package com.black.monkey.my.election;


import com.black.monkey.my.election.cmd.api.command.CloseCrvCommand;
import com.black.monkey.my.election.cmd.api.command.CommandHandler;
import com.black.monkey.my.election.cmd.api.command.NoteAddCommand;
import com.black.monkey.my.election.cmd.api.command.OpenCrvCommand;
import com.black.monkey.my.election.cmd.api.command.VoteRegistrationCommand;
import com.black.monkey.my.election.cmd.api.command.VoteUnRegistrationCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
import com.black.monkey.my.election.commons.event.CrvClosedEvent;
import com.black.monkey.my.election.commons.event.CrvOpenedEvent;
import com.black.monkey.my.election.commons.event.NoteAddedEvent;
import com.black.monkey.my.election.commons.event.VoteRegisteredEvent;
import com.black.monkey.my.election.commons.event.VoteUnRegisteredEvent;
import com.black.monkey.my.election.query.api.query.FindAllCrvQuery;
import com.black.monkey.my.election.query.api.query.FindAllNotesQuery;
import com.black.monkey.my.election.query.api.query.FindCrvByIdQuery;
import com.black.monkey.my.election.query.api.query.FindRegisteredVotesQuery;
import com.black.monkey.my.election.query.hanlder.EventHandler;
import com.black.monkey.my.election.query.infraestructure.EventDispatcher;
import com.black.monkey.my.election.query.infraestructure.QueryDispatcher;
import com.black.monkey.my.election.query.infraestructure.QueryHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


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

    @Autowired
    private QueryDispatcher queryDispatcher;

    @Autowired
    private QueryHandler queryHandler;

    @PostConstruct
    public void registerHandler() {
        commandDispatcher.registerHandler(OpenCrvCommand.class, commandHandler::handler);
        commandDispatcher.registerHandler(CloseCrvCommand.class, commandHandler::handler);
        commandDispatcher.registerHandler(VoteRegistrationCommand.class, commandHandler::handler);
        commandDispatcher.registerHandler(VoteUnRegistrationCommand.class, commandHandler::handler);
        commandDispatcher.registerHandler(NoteAddCommand.class, commandHandler::handler);

        eventDispatcher.registerHandler(CrvOpenedEvent.class, eventHandler::handler);
        eventDispatcher.registerHandler(CrvClosedEvent.class, eventHandler::handler);
        eventDispatcher.registerHandler(VoteRegisteredEvent.class, eventHandler::handler);
        eventDispatcher.registerHandler(VoteUnRegisteredEvent.class, eventHandler::handler);
        eventDispatcher.registerHandler(NoteAddedEvent.class, eventHandler::handler);

        queryDispatcher.registerHandler(FindCrvByIdQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindRegisteredVotesQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAllCrvQuery.class, queryHandler::handle);
        queryDispatcher.registerHandler(FindAllNotesQuery.class, queryHandler::handle);
    }

}

@Component
@Slf4j
class AppRunner implements ApplicationRunner {

    private final DataSource dataSource;

    public AppRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("URL {}",dataSource.getConnection().getMetaData().getURL());
    }
}
