package com.leadnile.organization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leadnile.organization.entity.Doctors;

@Repository
public interface DoctorsDao extends JpaRepository<Doctors, Integer>   {

}
