package com.leadnile.organization.service;

import com.leadnile.organization.entity.User;

public interface UserService {
    public User createUser(User user);
    public User getExistingUser(String number, Integer doctorId);
    public User getExistingDoctor(String email, Integer roleId);
}
