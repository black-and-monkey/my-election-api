package com.black.monkey.my.election.cmd.api.controller;

import com.black.monkey.my.election.cmd.api.command.OpenCrvCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
import com.black.monkey.my.election.commons.api.security.PermissionHelper;
import com.black.monkey.my.election.commons.client.Auth0Client;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(path ="/api/v1/open-crv")
@Slf4j
@RequiredArgsConstructor
public class OpenCrvController {

    private final CommandDispatcher commandDispatcher;
    private final Auth0Client auth0Client;
    private final PermissionHelper permissionHelper;

    @PostMapping
    public ResponseEntity openCrv() {

        OpenCrvCommand command = new OpenCrvCommand();
        command.setId(auth0Client.getUserCrv());

        commandDispatcher.send(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/by-id")
    public ResponseEntity openById(@RequestParam(value = "id") String id, HttpServletRequest request) {
        permissionHelper.hasAuthority(request);
        OpenCrvCommand command = new OpenCrvCommand();
        command.setId(id);
        commandDispatcher.send(command);
        return ResponseEntity.ok().build();
    }

}
