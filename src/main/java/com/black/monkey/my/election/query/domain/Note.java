package com.black.monkey.my.election.query.domain;

import com.black.monkey.my.election.core.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "note", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note extends BaseEntity {

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Id
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String note;

    private LocalDateTime timestamp;

    @OneToOne
    private MyUser registeredBy;

    @OneToOne
    private Crv crv;
}
