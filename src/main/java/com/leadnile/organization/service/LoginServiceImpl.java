package com.leadnile.organization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadnile.organization.dao.LoginDao;
import com.leadnile.organization.entity.LoginHistory;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    @Override
    public LoginHistory save(LoginHistory loginHistory) {
        return loginDao.save(loginHistory);
    }

    @Override
    public LoginHistory getLoginHistory(Integer userId, Integer doctorId, Integer loginId) {
        return loginDao.getLoginHistoryData(userId, doctorId, loginId);
    }
}
