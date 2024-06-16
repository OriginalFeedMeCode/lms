package com.leadnile.organization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.leadnile.organization.entity.LoginHistory;

public interface LoginDao extends JpaRepository<LoginHistory, Integer> {

    @Query(value = "SELECT * FROM LOGIN_HISTORY WHERE USER_ID = ?1 AND DOCTOR_ID = ?2 AND ID =?3 LIMIT 1", nativeQuery = true)
    public LoginHistory getLoginHistoryData(Integer userId, Integer doctorId, Integer LoginId);

}
