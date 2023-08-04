package com.black.monkey.my.election.cmd.api.controller;

import com.black.monkey.my.election.cmd.api.command.VoteRegistrationCommand;
import com.black.monkey.my.election.cmd.api.command.VoteUnRegistrationCommand;
import com.black.monkey.my.election.cmd.infraestructure.CommandDispatcher;
import com.black.monkey.my.election.commons.api.security.PermissionHelper;
import com.black.monkey.my.election.commons.client.Auth0Client;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path ="/api/v1/vote-registration")
@Slf4j
@RequiredArgsConstructor
public class VoteRegistrationController {

    private final CommandDispatcher commandDispatcher;
    private final Auth0Client auth0Client;
    private final PermissionHelper permissionHelper;

    @PostMapping
    public ResponseEntity voteRegistration(@RequestBody VoteRegistrationCommand command) {

        command.setId(auth0Client.getUserCrv());

        commandDispatcher.send(command);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(params = { "ci"})
    public ResponseEntity voteRegistrationDelete(@RequestParam(value = "ci") String ci, HttpServletRequest request) {

        permissionHelper.hasAuthority(request);

        VoteUnRegistrationCommand command = new VoteUnRegistrationCommand();
        command.setId(auth0Client.getUserCrv());
        command.setCi(ci);
        commandDispatcher.send(command);
        return ResponseEntity.ok().build();

    }

}
