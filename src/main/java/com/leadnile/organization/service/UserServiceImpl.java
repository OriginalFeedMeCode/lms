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
    public ResponseEntity<String> createUser(String request) {
        JSONObject response = new JSONObject();
        try {
            
            if (!ValidatorUtil.isValid(request)){
                response.put("status", "failed");
                response.put("message", "empty request");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }

            JSONObject data = new JSONObject(request);

            String name = data.optString("fName");
            String email = data.optString("email");
            String password = data.optString("password");
            String ogType = data.optString("ogType");

            if (!ValidatorUtil.isValid(name) || !ValidatorUtil.isValid(ogType) || !ValidatorUtil.isValid(email) || !ValidatorUtil.isValid(password)){
                response.put("status", "failed");
                response.put("message", "required fields are empty");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }

            User user = new User();
            user.setActive("Y");
            user.setDeleted("N");
            user.setVerifyStatus("N");
            user.setFirstName(name);
            user.setEmail(email);
            user.setOrganisationType(ogType);
            user.setPassword(password);
            user.setCreatedDate(new Date());
            userDao.save(user);
            response.put("status", "success");
            response.put("message", "user created successfully");
            return ResponseUtil.createOkResponse(response.toString());

        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "something wrong happen");
            return ResponseUtil.createInternalServerErrorResponse(response.toString());
        }
    }

    @Override
    public ResponseEntity<String> getUser(String request) {
        JSONObject response = new JSONObject();
        try {
            
            if (!ValidatorUtil.isValid(request)){
                response.put("status", "failed");
                response.put("message", "empty request");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }

            JSONObject data = new JSONObject(request);

            Integer id = data.optInt("userId");

            if (!ValidatorUtil.isValid(id)){
                response.put("status", "failed");
                response.put("message", "required fields are empty");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }

            Optional<User> user = userDao.findById(id);

            if (!ValidatorUtil.isValid(user.get())){
                response.put("status", "failed");
                response.put("message", "user not found");
                return ResponseUtil.createOkResponse(response.toString());
            }

            response.put("status", "success");
            response.put("message", "user found");
            response.put("user", user.get());
            return ResponseUtil.createOkResponse(response.toString());

        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "something wrong happen");
            return ResponseUtil.createInternalServerErrorResponse(response.toString());
        }
    }
    
}
