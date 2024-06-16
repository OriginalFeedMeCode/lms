package com.leadnile.organization.util;

import java.util.Date;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.leadnile.organization.constants.RoleConstant;
import com.leadnile.organization.entity.Doctors;
import com.leadnile.organization.entity.LoginHistory;
import com.leadnile.organization.entity.User;
import com.leadnile.organization.service.DoctorsService;
import com.leadnile.organization.service.UserService;
import com.leadnile.organization.service.LoginService;

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

    @Autowired
    private LoginService loginService;

    public ResponseEntity<String> createDoctorUser(String request) {
        JSONObject response = new JSONObject();
        try {
            if (!ValidatorUtil.isValid(request)) {
                response.put("status", "failed");
                response.put("message", "empty request");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            JSONObject data = new JSONObject(cryptionUtil.decodeFromBase64(request));
            String name = data.optString("firstName").trim();
            String email = data.optString("userEmail").toLowerCase().trim();
            String phoneNumber = data.optString("phoneNumber").trim();
            String password = data.optString("userCredential").trim();
            String phoneCode = data.optString("phoneCode").trim();
            if (!ValidatorUtil.isValid(name) || !ValidatorUtil.isValid(email) || !ValidatorUtil.isValid(password)) {
                response.put("status", "failed");
                response.put("message", "required fields are empty");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            User user = userService.getExistingDoctor(email, RoleConstant.ROLE_DOCTOR);
            if (ValidatorUtil.isValid(user)) {
                response.put("status", "failed");
                response.put("message", "Doctor Already Exists");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            user = new User();
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

    public ResponseEntity<String> loginDoctor(String request) {
        JSONObject response = new JSONObject();
        try {
            if (!ValidatorUtil.isValid(request)) {
                response.put("status", "failed");
                response.put("message", "empty request");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            // JSONObject data = new JSONObject(cryptionUtil.decodeFromBase64(request));
            JSONObject data = new JSONObject(request);
            String email = data.optString("userEmail");
            String password = data.optString("userCredential");
            User user = userService.getExistingDoctor(email, RoleConstant.ROLE_DOCTOR);
            if (!ValidatorUtil.isValid(user)) {
                response.put("status", "failed");
                response.put("message", "Doctor with this data not found");
                return ResponseUtil.createNotFoundResponse(response.toString());
            }
            if (user.getVerifyStatus().equalsIgnoreCase("N")) {
                response.put("status", "failed");
                response.put("message", "Please verify your account check your Email");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            if (!user.getPassword().equals(aesUtil.encryptExternalParam(password.trim()))) {
                response.put("status", "failed");
                response.put("message", "Please recheck your Password");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            LoginHistory loginHistory = new LoginHistory();
            loginHistory.setDoctorId(user.getDoctorId());
            loginHistory.setLoginDateTime(new Date());
            loginHistory.setUserId(user.getId());
            loginService.save(loginHistory);
            response.put("status", "success");
            response.put("message", "Successfully login");
            response.put("userId", user.getId());
            response.put("loginId", loginHistory.getId());
            response.put("email", user.getEmail());
            response.put("roleId", user.getRoleId());
            return ResponseUtil.createOkResponse(response.toString());
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "Something Went Wrong");
            return ResponseUtil.createInternalServerErrorResponse(response.toString());
        }
    }

    public ResponseEntity<String> logoutDoctor(String request) {
        JSONObject response = new JSONObject();
        try {
            if (!ValidatorUtil.isValid(request)) {
                response.put("status", "failed");
                response.put("message", "empty request");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            // JSONObject data = new JSONObject(cryptionUtil.decodeFromBase64(request));
            JSONObject data = new JSONObject(request);
            Integer userId = data.optInt("userId");
            Integer doctorId = data.optInt("doctorId");
            Integer loginId = data.optInt("loginId");
            LoginHistory loginHistory = loginService.getLoginHistory(userId, doctorId, loginId);
            if (!ValidatorUtil.isValid(loginHistory)) {
                response.put("status", "failed");
                response.put("message", "Technical Glitch Occured");
                return ResponseUtil.createBadRequestResponse(response.toString());
            }
            loginHistory.setLogoutDateTime(new Date());
            loginService.save(loginHistory);
            response.put("status", "success");
            response.put("message", "Successfully logout");
            return ResponseUtil.createOkResponse(response.toString());
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "Something Went Wrong");
            return ResponseUtil.createInternalServerErrorResponse(response.toString());
        }
    }
}
