package com.black.monkey.my.election.cmd.api.controller;

import com.black.monkey.my.election.cmd.api.command.NoteAddCommand;
import com.black.monkey.my.election.cmd.api.command.OpenCrvCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
import com.black.monkey.my.election.commons.client.Auth0Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/api/v1/note")
@Slf4j
@RequiredArgsConstructor
public class NoteController {

    private final CommandDispatcher commandDispatcher;
    private final Auth0Client auth0Client;

    @PostMapping
    public ResponseEntity addNote(@RequestBody NoteAddCommand command) {

        command.setId(auth0Client.getUserCrv());

        commandDispatcher.send(command);
        return ResponseEntity.ok().build();
    }

}
