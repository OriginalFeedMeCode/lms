package com.leadnile.organization.service;

import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<String> createUser(String request);

    public ResponseEntity<String> getUser(String request);
    
}
