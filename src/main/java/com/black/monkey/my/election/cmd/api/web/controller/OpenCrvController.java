package com.black.monkey.my.election.cmd.api.web.controller;

import com.black.monkey.my.election.cmd.api.web.command.OpenCrvCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="/api/v1/open-crv")
@Slf4j
@RequiredArgsConstructor
public class OpenCrvController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity openCrv(@RequestBody OpenCrvCommand command) {
        commandDispatcher.send(command);
        return ResponseEntity.ok().build();
    }

}
