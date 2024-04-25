package com.leadnile.organization.util;

import org.springframework.stereotype.Service;

import com.leadnile.organization.entity.User;

@Service
public class UserUtil {
    
    static String getFullName(User user){
        return user.getFirstName() + (user.getMiddleName() != null ? " "+ user.getMiddleName() : "") + (user.getLastName() != null ? " "+ user.getLastName() : "");
    }

}
