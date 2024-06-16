package com.leadnile.organization.util;

import java.util.Base64;
import org.springframework.stereotype.Service;

@Service
public class CryptionUtil {

    public String encodeToBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public String decodeFromBase64(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new String(decodedBytes);
    }

}
