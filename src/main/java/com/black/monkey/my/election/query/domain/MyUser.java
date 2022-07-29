package com.black.monkey.my.election.query.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "my_user", schema = "my_election")
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {

    @Id
    private String email;

    private String sub;
}
