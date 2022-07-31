package com.black.monkey.my.election.cmd.api.controller;

import com.black.monkey.my.election.cmd.api.command.CloseCrvCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
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

    @PutMapping(path = "/{id}")
    public ResponseEntity close(@PathVariable(value = "id") String id) {
        commandDispatcher.send(CloseCrvCommand.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
