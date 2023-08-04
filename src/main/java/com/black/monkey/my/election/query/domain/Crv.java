package com.black.monkey.my.election.query.domain;

import com.black.monkey.my.election.core.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "crv", schema = "public")
@Getter
@Setter
@ToString
public class Crv extends BaseEntity {

    @Id
    private String id;

    private String description;

    private LocalDateTime openedAt;

    private boolean isOpened;

    @OneToOne(fetch = FetchType.EAGER)
    private MyUser openedBy;

    private LocalDateTime closedAt;

    @OneToOne(fetch = FetchType.EAGER)
    private MyUser closedBy;

    @Enumerated(EnumType.STRING)
    private Departamento departamento;

    private String localidad;

}
