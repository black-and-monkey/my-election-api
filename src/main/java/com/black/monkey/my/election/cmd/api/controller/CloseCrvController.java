package com.black.monkey.my.election.cmd.api.controller;

import com.black.monkey.my.election.cmd.api.command.CloseCrvCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
import com.black.monkey.my.election.commons.api.security.PermissionHelper;
import com.black.monkey.my.election.commons.client.Auth0Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path ="/api/v1/close-crv")
@Slf4j
@RequiredArgsConstructor
public class CloseCrvController {

    private final CommandDispatcher commandDispatcher;
    private final Auth0Client auth0Client;
    private final PermissionHelper permissionHelper;

    @PutMapping
    public ResponseEntity close(@RequestBody CloseCrvCommand command) {
        command.setId(auth0Client.getUserCrv());
        commandDispatcher.send(command);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/by-id")
    public ResponseEntity closeById(@RequestParam(value = "id") String id, HttpServletRequest request) {
        permissionHelper.hasAuthority(request);
        commandDispatcher.send(CloseCrvCommand.builder().id(id).build());
        return ResponseEntity.ok().build();
    }

}
