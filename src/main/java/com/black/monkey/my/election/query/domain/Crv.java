package com.black.monkey.my.election.query.domain;

import com.black.monkey.my.election.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "crv", schema = "public")
@Getter
@Setter
public class Crv extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    private Departamento departamento;

    private String localidad;

}
