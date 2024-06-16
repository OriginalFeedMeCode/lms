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

    @Autowired
    private CryptionUtil cryptionUtil;

    @Autowired
    private AesUtil aesUtil;

    public ResponseEntity<String> createDoctorUser(String request) {
        JSONObject response = new JSONObject();
        try {
            if (!ValidatorUtil.isValid(request)) {
                response.put("status", "failed");
                response.put("message", "empty request");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            JSONObject data = new JSONObject(cryptionUtil.decodeFromBase64(request));
            String name = data.optString("firstName");
            String email = data.optString("userEmail").toLowerCase().trim();
            String phoneNumber = data.optString("phoneNumber").trim();
            String password = data.optString("userCredentials");
            String phoneCode = data.optString("phoneCode");
            if (!ValidatorUtil.isValid(name) || !ValidatorUtil.isValid(email) || !ValidatorUtil.isValid(password)) {
                response.put("status", "failed");
                response.put("message", "required fields are empty");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            User user = new User();
            User alreadyUser = userService.getExistingDoctor(email, RoleConstant.ROLE_DOCTOR);
            if (ValidatorUtil.isValid(alreadyUser)) {
                response.put("status", "failed");
                response.put("message", "Doctor Already Exists");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            user.setRoleId(RoleConstant.ROLE_DOCTOR);
            user.setActive("Y");
            user.setDeleted("N");
            user.setPhoneCode(phoneCode);
            user.setPhoneNumber(phoneNumber);
            user.setVerifyStatus("N");
            user.setFirstName(name);
            user.setEmail(email);
            user.setPassword(aesUtil.encryptExternalParam(password));
            user.setCreatedDate(new Date());
            userService.createUser(user);
            Doctors doctor = new Doctors();
            doctor.setActive("N");
            doctor.setDeleted("N");
            doctor.setUserId(user.getId());
            doctorsService.createDoctor(doctor);
            user.setDoctorId(doctor.getId());
            userService.createUser(user);
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
