package com.leadnile.organization.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOGIN_HISTORY")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "DOCTOR_ID")
    private Integer doctorId;

    @Column(name = "LOGIN_DATE_TIME")
    private Date loginDateTime;

    @Column(name = "LOGOUT_DATE_TIME")
    private Date logoutDateTime;

    @Column(name = "INFORMATION")
    private String information;
}
