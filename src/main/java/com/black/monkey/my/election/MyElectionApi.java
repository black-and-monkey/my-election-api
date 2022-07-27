package com.black.monkey.my.election;


import com.black.monkey.my.election.cmd.api.web.command.CloseCrvCommand;
import com.black.monkey.my.election.cmd.api.web.command.CommandHandler;
import com.black.monkey.my.election.cmd.api.web.command.OpenCrvCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EntityScan("com.black.monkey.my.election.cmd.domain")
public class MyElectionApi {

    public static void main(String[] args) {
        SpringApplication.run(MyElectionApi.class, args);
    }

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private CommandHandler commandHandler;

    @PostConstruct
    public void registerHandler() {
        commandDispatcher.registerHandler(OpenCrvCommand.class, commandHandler::handler);
        commandDispatcher.registerHandler(CloseCrvCommand.class, commandHandler::handler);
    }

}
