package com.leadnile.organization.util;

import java.util.Date;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.leadnile.organization.constants.RoleConstant;
import com.leadnile.organization.entity.Doctors;
import com.leadnile.organization.entity.User;
import com.leadnile.organization.service.DoctorsService;
import com.leadnile.organization.service.UserService;

@Service
public class UserUtil {

    @Autowired
    private UserService userService;
    
    @Autowired
    private DoctorsService doctorsService;

    public ResponseEntity<String> createUser(String request){
        JSONObject response = new JSONObject();
        try {
            
            if (!ValidatorUtil.isValid(request)){
                response.put("status", "failed");
                response.put("message", "empty request");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }

            String decryptRequest = CipherUtil.decrypt(request);

            JSONObject data = new JSONObject(decryptRequest);

            String name = data.optString("fName");
            String email = data.optString("email").toLowerCase().trim();
            String phoneNumber = data.optString("pNumber").trim();
            String password = data.optString("password");
            if (ValidatorUtil.isValid(password)){
                password = CipherUtil.encrypt(password);
            }
            Integer roleId = data.optInt("roleId");
            Integer doctorId;

            User user = new User();

            if (roleId == RoleConstant.ROLE_PATIENT){
                doctorId = data.optInt("doctorId");
                if (!ValidatorUtil.isValid(doctorId)){
                    response.put("status", "failed");
                    response.put("message", "Bad Request");
                    return ResponseUtil.createBadRequestResponse(response.toString());
                }
                User alreadyUser = userService.getExistingUser(phoneNumber, doctorId);
                if (ValidatorUtil.isValid(alreadyUser)){
                    response.put("status", "failed");
                    response.put("message", "Patient Already Exists");
                    return ResponseUtil.createBadRequestResponse(response.toString());
                }
                user.setDoctorId(doctorId);
            }else if (roleId == RoleConstant.ROLE_DOCTOR){
                User alreadyUser = userService.getExistingUser(email, RoleConstant.ROLE_DOCTOR);
                if (ValidatorUtil.isValid(alreadyUser)){
                    response.put("status", "failed");
                    response.put("message", "Doctor Already Exists");
                    return ResponseUtil.createOkResponse(response.toString());
                }
            }
            
            user.setRoleId(roleId);
            user.setActive("N");
            user.setDeleted("N");
            user.setVerifyStatus("N");
            user.setFirstName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setCreatedDate(new Date());
            userService.createUser(user);

            if (roleId == RoleConstant.ROLE_DOCTOR){
                Doctors doctor = new Doctors();
                doctor.setActive("N");
                doctor.setDeleted("N");
                doctor.setUserId(user.getId());
                doctorsService.createDoctor(doctor);
                user.setDoctorId(doctor.getId());
                userService.createUser(user);
            }

            if (!ValidatorUtil.isValid(name) || !ValidatorUtil.isValid(email) || !ValidatorUtil.isValid(password)){
                response.put("status", "failed");
                response.put("message", "required fields are empty");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }

            response.put("status", "success");
            response.put("message", "User Created Successfully");
            return ResponseUtil.createOkResponse(response.toString());

        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "Something Went Wrong");
            return ResponseUtil.createInternalServerErrorResponse(response.toString());
        }
    }

}
