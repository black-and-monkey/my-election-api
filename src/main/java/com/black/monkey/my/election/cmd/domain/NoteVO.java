package com.black.monkey.my.election.cmd.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@ToString
public class NoteVO {

    private LocalDateTime timestamp;
    private String note;
    private String registerBy;

}
