package com.black.monkey.my.election.query.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
public class RegisteredVotesResponse {

    private int total;

    private List<RegisteredVoteResponse> votes;

    @Builder
    @Getter
    @Setter
    public static class RegisteredVoteResponse {
        private long voteNumber;
        private String fullName;
        private LocalDate dob;
        private String ci;
    }
}
