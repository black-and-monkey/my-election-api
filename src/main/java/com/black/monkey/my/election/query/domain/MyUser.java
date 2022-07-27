package com.black.monkey.my.election.query.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "my_user", schema = "my_election")
public class MyUser {

    @Id
    private String email;
}
