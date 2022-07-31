package com.black.monkey.my.election.query.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CrvLookupResponse {

    private String id;
    private String description;
    private boolean isOpened;
}
