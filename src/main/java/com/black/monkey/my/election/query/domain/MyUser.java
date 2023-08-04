package com.black.monkey.my.election.query.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "my_user", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {

    @Id
    private String email;

    private String sub;
}
