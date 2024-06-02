package com.leadnile.organization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadnile.organization.dao.DoctorsDao;
import com.leadnile.organization.entity.Doctors;

@Service
public class DoctorsServiceImpl implements DoctorsService {

    @Autowired 
    private DoctorsDao doctorsDao;

    @Override
    public Doctors createDoctor(Doctors doctors) {
       return doctorsDao.save(doctors);
    }

    
}
