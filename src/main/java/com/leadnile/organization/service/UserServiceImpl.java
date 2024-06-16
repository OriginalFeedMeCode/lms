package com.leadnile.organization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadnile.organization.dao.UserDao;
import com.leadnile.organization.entity.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User createUser(User user) {
        userDao.save(user);
        return user;
    }

    @Override
    public User getExistingUser(String number, Integer doctorId) {
        return userDao.getExistingUser(number, doctorId);
    }

    @Override
    public User getExistingDoctor(String email, Integer roleId) {
        return userDao.getExistingDoctor(email, roleId);
    }

    @SuppressWarnings("deprecation")
    @Override
    public User getUser(Integer userId) {
        return userDao.getById(userId);
    }

}
