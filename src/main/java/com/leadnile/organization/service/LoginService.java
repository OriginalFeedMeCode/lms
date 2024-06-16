package com.leadnile.organization.service;

import com.leadnile.organization.entity.LoginHistory;

public interface LoginService {
    public LoginHistory save(LoginHistory loginHistory);

    public LoginHistory getLoginHistory(Integer userId, Integer doctorId, Integer loginId);
}
