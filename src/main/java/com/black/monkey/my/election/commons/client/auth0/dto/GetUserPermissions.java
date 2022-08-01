package com.black.monkey.my.election.commons.client.auth0.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GetUserPermissions {

    private List<Permission> permissions;

    private int start;
    private int limit;
    private int total;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Permission {
        @JsonProperty("permission_name")
        private String permissionName;

        @JsonProperty("description")
        private String description;

        // ignoring the others fields
    }
}
