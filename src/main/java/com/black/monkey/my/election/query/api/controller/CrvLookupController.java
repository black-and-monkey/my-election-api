package com.black.monkey.my.election.query.api.controller;

import com.black.monkey.my.election.commons.api.security.PermissionHelper;
import com.black.monkey.my.election.commons.client.Auth0Client;
import com.black.monkey.my.election.query.api.dto.CrvLookupResponse;
import com.black.monkey.my.election.query.api.dto.FindAllCrvResponse;
import com.black.monkey.my.election.query.api.dto.FindNotesResponse;
import com.black.monkey.my.election.query.api.dto.RegisteredVotesResponse;
import com.black.monkey.my.election.query.api.query.FindAllCrvQuery;
import com.black.monkey.my.election.query.api.query.FindAllNotesQuery;
import com.black.monkey.my.election.query.api.query.FindCrvByIdQuery;
import com.black.monkey.my.election.query.api.query.FindRegisteredVotesQuery;
import com.black.monkey.my.election.query.domain.Crv;
import com.black.monkey.my.election.query.domain.Note;
import com.black.monkey.my.election.query.domain.VoteRegistration;
import com.black.monkey.my.election.query.infraestructure.QueryDispatcher;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/crv-lookup")
@RequiredArgsConstructor
@Slf4j
public class CrvLookupController {

    private final QueryDispatcher queryDispatcher;
    private final Auth0Client auth0Client;
    private final PermissionHelper permissionHelper;

    @GetMapping(path = "/find-all", params = { "page", "size" })
    public ResponseEntity<FindAllCrvResponse> getAllCrv(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "20") int size,
                                                        HttpServletRequest request) {
        permissionHelper.hasAuthority(request);
        Page<Crv> crvs = queryDispatcher.send(FindAllCrvQuery.builder()
                .page(page)
                .size(size)
                .build());
        List<FindAllCrvResponse.CrvLookupResponse> responseList = new ArrayList<>(crvs.getSize());

        for (Crv crv: crvs.getContent()) {
            responseList.add(FindAllCrvResponse.CrvLookupResponse.builder()
                    .description(crv.getDescription())
                    .id(crv.getId())
                    .isOpened(crv.isOpened())
                    .openBy(crv.getOpenedBy()!= null ? crv.getOpenedBy().getEmail() : null)
                    .closeBy(crv.getClosedBy() != null ? crv.getClosedBy().getEmail(): null)
                    .closeTimestamp(crv.getClosedAt())
                    .openTimestamp(crv.getOpenedAt())
                    .departamento(crv.getDepartamento())
                    .locallidad(crv.getLocalidad())
                    .build());
        }
        return new ResponseEntity<>(FindAllCrvResponse.builder()
                .crvs(responseList)
                .total(crvs.getTotalElements())
                .build(),
                HttpStatus.OK);
    }

    @GetMapping(path = "/find-notes", params = { "id", "page", "size" })
    public ResponseEntity<FindNotesResponse> getAllNotesByCrv(@RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "size", defaultValue = "20") int size,
                                                              @RequestParam(value = "id") String crvId,
                                                              HttpServletRequest request) {
        permissionHelper.hasAuthority(request);

        Page<Note> notes = queryDispatcher.send(FindAllNotesQuery.builder()
                .page(page)
                .size(size)
                .crvId(crvId)
                .build());

        List<FindNotesResponse.Note> responseList = new ArrayList<>(notes.getSize());

        for (Note note: notes.getContent()) {
            responseList.add(FindNotesResponse.Note.builder()
                    .note(note.getNote())
                    .noteBy(note.getRegisteredBy().getEmail())
                    .timestamp(note.getTimestamp())
                    .build());
        }
        return new ResponseEntity<>(FindNotesResponse.builder()
                .notes(responseList)
                .total(notes.getTotalElements())
                .build(),
                HttpStatus.OK);
    }


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
        return new ResponseEntity<>(getVotes(auth0Client.getUserCrv(),page, size), HttpStatus.OK);
    }


    @GetMapping(path = "/registered-votes/by-id", params = { "id", "page", "size" })
    public ResponseEntity<RegisteredVotesResponse> getRegisteredVotesByCrv(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                           @RequestParam(value = "size", defaultValue = "20") int size,
                                                                           @RequestParam(value = "id") String crvId,
                                                                           HttpServletRequest request) {
        permissionHelper.hasAuthority(request);
        return new ResponseEntity<>(getVotes(crvId, page, size), HttpStatus.OK);
    }

    private RegisteredVotesResponse getVotes(String crvId, int page, int size) {
        Page<VoteRegistration> votes = queryDispatcher.send(FindRegisteredVotesQuery.builder()
                .crvId(crvId)
                .page(page)
                .size(size)
                .build());

        RegisteredVotesResponse response = RegisteredVotesResponse.builder()
                .total(votes.getTotalElements())
                .votes(new ArrayList<>(votes.getSize()))
                .build();

        for (VoteRegistration vote: votes.getContent()) {
            response.getVotes().add(RegisteredVotesResponse.RegisteredVoteResponse.builder()
                    .ci(vote.getCi())
                    .dob(vote.getDob())
                    .fullName(vote.getFullName())
                    .voteNumber(vote.getVoteNumber())
                    .timestamp(vote.getTimestamp())
                    .build());
        }
        return response;
    }
}


