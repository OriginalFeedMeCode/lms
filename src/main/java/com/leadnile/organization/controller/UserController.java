package com.leadnile.organization.controller;

import org.springframework.web.bind.annotation.RestController;

import com.leadnile.organization.service.UserService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup-user-s1")
    public ResponseEntity<String> createUser( RequestEntity<String> request ) {
        return userService.createUser(request.getBody());
    }

    @PostMapping("/get-user")
    public ResponseEntity<String> getUser( RequestEntity<String> request ) {
        return userService.getUser(request.getBody());
    }


    
    

}
