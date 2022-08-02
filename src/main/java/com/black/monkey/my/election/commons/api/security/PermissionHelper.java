package com.black.monkey.my.election.commons.api.security;

import com.black.monkey.my.election.commons.client.Auth0Client;
import com.black.monkey.my.election.commons.client.auth0.dto.GetUserPermissions;
import com.black.monkey.my.election.core.exceptions.NoPermissionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionHelper {

    private final Auth0Client auth0Client;

    public void hasAuthority(HttpServletRequest request) {

        String verbPath = String.format("%s:%s", StringUtils.lowerCase(request.getMethod()), request.getServletPath());
        log.debug("checking rights for {}",verbPath);

        for (GetUserPermissions.Permission permission : auth0Client.getUserPermissions().getPermissions()) {
            if (verbPath.equals(permission.getPermissionName())) {
                return;
            }
        }
        throw new NoPermissionException(MessageFormat.format("no permissions for accessing {0}",verbPath));
    }
}
