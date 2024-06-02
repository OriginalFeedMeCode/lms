package com.leadnile.organization.controller;

import org.springframework.web.bind.annotation.RestController;
import com.leadnile.organization.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserUtil userUtil;

    @PostMapping("/signup-user-s1")
    public ResponseEntity<String> createUser( RequestEntity<String> request ) {
        return userUtil.createUser(request.getBody());
    }

}
