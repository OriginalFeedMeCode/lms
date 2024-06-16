package com.leadnile.organization.util;

import java.net.URLDecoder;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CryptionUtil {

    public String encodeToBase64(String input) {
        return new String(Base64.getEncoder().encode(input.getBytes()));
    }

    public String decodeFromBase64(String payload) {
        if (!ValidatorUtil.isValid(payload)) {
            return payload;
        }
        try {
            payload = payload.replaceAll(" ", "+");
            payload = new String(java.util.Base64.getDecoder().decode(payload));
            payload = URLDecoder.decode(payload, "UTF-8");
            return payload;
        } catch (Exception e) {
            log.error("decode exception caught :: " + e);
        }
        return StringUtils.EMPTY;
    }

}
