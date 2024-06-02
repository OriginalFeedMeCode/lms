package com.leadnile.organization.service;

import java.util.Date;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.leadnile.organization.dao.UserDao;
import com.leadnile.organization.entity.User;
import com.leadnile.organization.util.ResponseUtil;
import com.leadnile.organization.util.ValidatorUtil;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Override
    public User createUser(User user) {
        userDao.save(user);
        return user;
    }

    
}
