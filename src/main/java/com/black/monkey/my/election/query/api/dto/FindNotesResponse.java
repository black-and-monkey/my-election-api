package com.black.monkey.my.election.query.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class FindNotesResponse {

    private int total;

    private List<FindNotesResponse.Note> notes;

    @Builder
    @Getter
    @Setter
    public static class Note {

        private String note;
        private String noteBy;
        private LocalDateTime timestamp;
    }

}
