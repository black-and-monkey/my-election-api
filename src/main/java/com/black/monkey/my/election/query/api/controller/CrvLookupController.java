package com.black.monkey.my.election.query.api.controller;

import com.black.monkey.my.election.commons.client.Auth0Client;
import com.black.monkey.my.election.query.api.dto.CrvLookupResponse;
import com.black.monkey.my.election.query.api.query.FindCrvByIdQuery;
import com.black.monkey.my.election.query.domain.Crv;
import com.black.monkey.my.election.query.infraestructure.QueryDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/crv-lookup")
@RequiredArgsConstructor
@Slf4j
public class CrvLookupController {

    private final QueryDispatcher queryDispatcher;
    private final Auth0Client auth0Client;

    @GetMapping(path = "/my-crv")
    public ResponseEntity<CrvLookupResponse> getCrv() {

            List<Crv> crvList = queryDispatcher.send(FindCrvByIdQuery.builder().id(auth0Client.getUserCrv()).build());

            if (crvList.isEmpty()) {
                log.error("user without crv {}",auth0Client.getUser().getEmail());
                return new ResponseEntity("no CRV associated", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(CrvLookupResponse.builder()
                    .description(crvList.get(0).getDescription())
                    .id(crvList.get(0).getId())
                    .isOpened(crvList.get(0).isOpened())
                    .build(), HttpStatus.OK);

    }

}


