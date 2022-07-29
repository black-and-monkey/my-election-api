package com.black.monkey.my.election.commons.client.auth0.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GetUserResponse {

    @JsonProperty("user_id")
    public String userId;

    @JsonProperty("email")
    public String email;

    @JsonProperty("email_verified")
    public Boolean emailVerified;

    @JsonProperty("username")
    public String username;

    @JsonProperty("phone_number")
    public String phoneNumber;

    @JsonProperty("phone_verified")
    public Boolean phoneVerified;

    @JsonProperty("created_at")
    public LocalDateTime createdAt;

    @JsonProperty("updated_at")
    public LocalDateTime updatedAt;

    @JsonProperty("identities")
    public List<Identity> identities = null;

    @JsonProperty("app_metadata")
    public Map<String, Object> appMetadata;

    @JsonProperty("user_metadata")
    public Map<String, Object> userMetadata;

    @JsonProperty("picture")
    public String picture;

    @JsonProperty("name")
    public String fullName;

    @JsonProperty("nickname")
    public String nickname;

    @JsonProperty("multifactor")
    public List<String> multifactor = null;

    @JsonProperty("last_ip")
    public String lastIp;

    @JsonProperty("last_login")
    public LocalDateTime lastLogin;

    @JsonProperty("logins_count")
    public Integer loginsCount;

    @JsonProperty("blocked")
    public Boolean blocked;

    @JsonProperty("given_name")
    public String givenName;

    @JsonProperty("family_name")
    public String familyName;
}
