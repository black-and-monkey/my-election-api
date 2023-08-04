package com.black.monkey.my.election.query.domain;

import com.black.monkey.my.election.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "vote_registration", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteRegistration extends BaseEntity {

    @Id
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
    private UUID id;

    private String fullName;

    private LocalDateTime timestamp;

    private LocalDate dob;

    private String ci;

    @OneToOne
    private Crv crv;

    @OneToOne
    private MyUser registeredBy;

    private Long voteNumber;

}
