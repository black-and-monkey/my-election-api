package com.black.monkey.my.election.cmd.api.controller;

import com.black.monkey.my.election.cmd.api.command.CloseCrvCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
import com.black.monkey.my.election.commons.client.Auth0Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/api/v1/close-crv")
@Slf4j
@RequiredArgsConstructor
public class CloseCrvController {

    private final CommandDispatcher commandDispatcher;
    private final Auth0Client auth0Client;

    @PutMapping
    public ResponseEntity close() {
        commandDispatcher.send(CloseCrvCommand.builder().id(auth0Client.getUserCrv()).build());
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/by-id/{id}")
    public ResponseEntity closeById(@PathVariable(value = "id") String id) {
        // TODO check if an admin // not in the security yet
        commandDispatcher.send(CloseCrvCommand.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
