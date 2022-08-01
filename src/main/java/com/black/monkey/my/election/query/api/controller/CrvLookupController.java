package com.black.monkey.my.election.query.api.controller;

import com.black.monkey.my.election.commons.client.Auth0Client;
import com.black.monkey.my.election.query.api.dto.CrvLookupResponse;
import com.black.monkey.my.election.query.api.dto.RegisteredVotesResponse;
import com.black.monkey.my.election.query.api.query.FindCrvByIdQuery;
import com.black.monkey.my.election.query.api.query.FindRegisteredVotesQuery;
import com.black.monkey.my.election.query.domain.Crv;
import com.black.monkey.my.election.query.domain.VoteRegistration;
import com.black.monkey.my.election.query.infraestructure.QueryDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/v1/crv-lookup")
@RequiredArgsConstructor
@Slf4j
public class CrvLookupController {

    private final QueryDispatcher queryDispatcher;
    private final Auth0Client auth0Client;

    @GetMapping(path = "/my-crv")
    public ResponseEntity<CrvLookupResponse> getCrv() {

        Page<Crv> crvList = queryDispatcher.send(FindCrvByIdQuery.builder().id(auth0Client.getUserCrv()).build());

        if (crvList.isEmpty()) {
            log.error("user without crv {}",auth0Client.getUser().getEmail());
            return new ResponseEntity("no CRV associated", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(CrvLookupResponse.builder()
                .description(crvList.getContent().get(0).getDescription())
                .id(crvList.getContent().get(0).getId())
                .isOpened(crvList.getContent().get(0).isOpened())
                .build(), HttpStatus.OK);

    }

    @GetMapping(path = "/registered-votes", params = { "page", "size" })
    public ResponseEntity<RegisteredVotesResponse> getRegisteredVotes(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "size", defaultValue = "20") int size) {

        Page<VoteRegistration> votes = queryDispatcher.send(FindRegisteredVotesQuery.builder()
                .crvId(auth0Client.getUserCrv())
                .page(page)
                .size(size)
                .build());

        RegisteredVotesResponse response = RegisteredVotesResponse.builder()
                .total(votes.getNumberOfElements())
                .votes(new ArrayList<>(votes.getSize()))
                .build();

        for (VoteRegistration vote: votes.getContent()) {
            response.getVotes().add(RegisteredVotesResponse.RegisteredVoteResponse.builder()
                    .ci(vote.getCi())
                    .dob(vote.getDob())
                    .fullName(vote.getFullName())
                    .voteNumber(vote.getVoteNumber())
                    .build());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}


