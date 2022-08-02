package com.black.monkey.my.election.cmd.domain;

import com.black.monkey.my.election.core.event.BaseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events", schema = "my_election")
public class EventModel {

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

    private LocalDateTime timestamp;

    private String aggregateIdentifier;

    private String aggregateType;

    private int version;

    private String eventType;

    @Transient
    private BaseEvent eventData;

    @Column(columnDefinition = "TEXT")
    private String eventDataJson;

    public BaseEvent getEventData(ObjectMapper objectMapper) {
        try {
            return (BaseEvent) objectMapper.readValue(eventDataJson,Class.forName(eventType));
        } catch (Exception e) {
            throw new RuntimeException("couldn't recognize event " + eventType);
        }
    }

}
