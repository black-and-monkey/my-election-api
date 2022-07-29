package com.black.monkey.my.election.core.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseEvent {

    private int version;

    private String id;

    private String sub;

    private String email;

}
