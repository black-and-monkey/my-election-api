package com.black.monkey.my.election.query.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "crv", schema = "my_election")
@Getter
@Setter
public class Crv {

    @Id
    private String id;

    private String description;

    private LocalDateTime openedAt;

    private boolean isOpened;

    @OneToOne
    private MyUser openedBy;

    private LocalDateTime closedAt;

    @OneToOne
    private MyUser closedBy;

}
