package com.black.monkey.my.election.commons.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TokenHelper {

    private static final java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
    private static final ObjectMapper mapper = new ObjectMapper() ;

    public static Map<String,Object> decodeToken() {
        try {

            String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
            String[] parts = token.split("\\.");
            //String headerJson = new String(decoder.decode(parts[0]));
            String payloadJson = new String(decoder.decode(parts[1]));
            return mapper.readValue(payloadJson, HashMap.class);
        } catch (Exception e) {
            log.error("",e);
            throw new IllegalStateException("couldn't decoded request token");
        }
    }
}
