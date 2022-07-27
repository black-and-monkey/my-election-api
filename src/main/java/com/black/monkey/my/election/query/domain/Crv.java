package com.black.monkey.my.election.query.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "crv_aggregate", schema = "my_election")
public class Crv {

    @Id
    private String id;

}
