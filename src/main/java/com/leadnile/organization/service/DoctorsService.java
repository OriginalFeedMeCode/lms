package com.leadnile.organization.service;

import com.leadnile.organization.entity.Doctors;

public interface DoctorsService {

    public Doctors createDoctor(Doctors doctors);

    public Doctors getDoctor(Integer doctorId);

}