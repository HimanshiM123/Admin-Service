package com.bridgelabz.lmsadmin.repository;

import com.bridgelabz.lmsadmin.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAdminRepository extends JpaRepository<AdminModel, Long> {
    Optional<AdminModel> findAdminByEmailId(String email);
}
