package com.leadnile.organization.controller;

import org.springframework.web.bind.annotation.RestController;
import com.leadnile.organization.util.UserUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/v1/users/")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserUtil userUtil;

    @PostMapping("signup-doctor-s1")
    public ResponseEntity<String> createDoctorUser(RequestEntity<String> request) {
        return userUtil.createDoctorUser(request.getBody());
    }

    @GetMapping("activation")
    public ResponseEntity<String> activateAccount(@RequestParam String payload) {
        return userUtil.activateAccount(payload);
    }

}
