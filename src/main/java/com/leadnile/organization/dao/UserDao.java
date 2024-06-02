package com.leadnile.organization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leadnile.organization.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM USERS WHERE PHONE_NUMBER = ?1 AND DOCTOR_ID = ?2 ORDER BY CREATED_DATE DESC LIMIT 1",nativeQuery = true )
    public User getExistingUser(String phoneNumber, Integer doctorId);

    @Query(value = "SELECT * FROM USERS WHERE EMAIl = ?1 AND ROLE_ID = ?2 ORDER BY CREATED_DATE DESC LIMIT 1",nativeQuery = true )
    public User getExistingDoctor(String email, Integer roleId);
    
}
