package com.leadnile.organization.controller;

import org.springframework.web.bind.annotation.RestController;

import com.leadnile.organization.util.UserUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/v1/doctor/")
public class LoginController {

    @Autowired
    private UserUtil userUtil;

    @PostMapping("login")
    public ResponseEntity<String> doctorLogin(RequestEntity<String> request) {
        return userUtil.loginDoctor(request.getBody());
    }

    @PostMapping("log-out")
    public ResponseEntity<String> doctorLogoout(RequestEntity<String> request) {
        return userUtil.logoutDoctor(request.getBody());
    }

}
