package com.black.monkey.my.election.commons.client;

import com.black.monkey.my.election.commons.client.auth0.dto.GetUserResponse;
import com.black.monkey.my.election.commons.helper.TokenHelper;
import com.black.monkey.my.election.core.exceptions.UserWithoutCRVException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.black.monkey.my.election.commons.Constants.DEFAULT_ZONE_ID;

@Service
@Slf4j
public class Auth0Client {

    @Value("${auth0.management.api.baseurl}")
    private String baseUrl;

    @Value("${auth0.management.api.clientId}")
    private String clientId;

    @Value("${auth0.management.api.clientSecret}")
    private String clientSecret;

    @Value("${auth0.management.api.audience}")
    private String audience;

    private RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<LinkedHashMap> tokenResponse;

    private Instant tokenExpirationTime;

    @PostConstruct
    void init() {
        getToken();
    }

    public synchronized void getToken() {
        RestTemplate restTemplate = new RestTemplate();

        log.debug("clientId: {} , clientSecret: {}", clientId, clientSecret);

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("audience", audience);
        body.put("grant_type", "client_credentials");

        tokenResponse = restTemplate.exchange(baseUrl + "/oauth/token", HttpMethod.POST, new HttpEntity<>(body, buildHeaders()), LinkedHashMap.class);

        tokenExpirationTime = Instant.now(Clock.systemUTC()).plusSeconds(Long.parseLong(tokenResponse.getBody().get("expires_in").toString()));
        log.debug("expiration token time, expires in: {} seconds (at {} UTC / {} )", tokenResponse.getBody().get("expires_in"), tokenExpirationTime, tokenExpirationTime.atZone(DEFAULT_ZONE_ID));
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }

    private HttpHeaders buildHeadersForAuth0Api() {
        if (tokenResponse == null) {
            getToken();
        } else {
            refreshTokenCheck();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", tokenResponse.getBody().get("access_token").toString()));
        return headers;
    }

    /**
     * Token lifetime
     * A Management API token is valid for 24 hours. Create a new access token when the old one expires.
     * https://auth0.com/docs/tokens/management-api-access-tokens
     */
    private synchronized void refreshTokenCheck() {
        if (Instant.now(Clock.systemUTC()).until(tokenExpirationTime, ChronoUnit.SECONDS) < 120) {
            getToken();
        }
    }

    public ResponseEntity<GetUserResponse> getUser(String sub) {
        String url = String.format("%s/api/v2/users/%s?include_fields=true", baseUrl, sub);
        log.debug(url);
        return restTemplate.exchange(
                url,  // URL
                HttpMethod.GET,
                new HttpEntity<>(buildHeadersForAuth0Api()),
                GetUserResponse.class);
    }

    public GetUserResponse getUser() {

        // TODO add cache // REDIS ?

        ResponseEntity<GetUserResponse> userResponse = getUser(TokenHelper.decodeToken().get("sub").toString());

        if (userResponse.hasBody()) {
            return userResponse.getBody();
        }

       throw new IllegalStateException("couldn't get the user from auth0");
    }

    public String getUserCrv() {
        GetUserResponse user = getUser();

        if (!user.getAppMetadata().containsKey("crv")) {
            throw new UserWithoutCRVException(MessageFormat.format("user {0}", user.getEmail()));
        }
        return user.getAppMetadata().get("crv").toString();
    }
}
