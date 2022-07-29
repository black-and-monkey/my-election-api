package com.black.monkey.my.election.cmd.api.web.controller;

import com.black.monkey.my.election.cmd.api.web.command.OpenCrvCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
import com.black.monkey.my.election.commons.client.Auth0Client;
import com.black.monkey.my.election.commons.client.auth0.dto.GetUserResponse;
import com.black.monkey.my.election.core.exceptions.UserWithoutCRVException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping(path ="/api/v1/open-crv")
@Slf4j
@RequiredArgsConstructor
public class OpenCrvController {

    private final CommandDispatcher commandDispatcher;
    private final Auth0Client auth0Client;

    @PostMapping
    public ResponseEntity openCrv() {

        GetUserResponse user = auth0Client.getUser();

        if (!user.getAppMetadata().containsKey("crv")) {
            throw new UserWithoutCRVException(MessageFormat.format("user {0}", user.getEmail()));
        }

        OpenCrvCommand command = new OpenCrvCommand();
        command.setId(user.getAppMetadata().get("crv").toString());

        commandDispatcher.send(command);
        return ResponseEntity.ok().build();
    }

}
