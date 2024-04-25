package com.leadnile.organization.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static ResponseEntity<String> createOkResponse(String message) {
        return ResponseEntity.ok(message);
    }

    public static ResponseEntity<String> createNotFoundResponse(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    public static ResponseEntity<String> createForbiddenResponse(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    public static ResponseEntity<String> createBadRequestResponse(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    public static ResponseEntity<String> createInternalServerErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}

