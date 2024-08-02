package com.example.ParlourApp.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminRegModel,Long> {
    Optional<AdminRegModel> findFirstByAdminName(String adminName);

    Optional<AdminRegModel> findByEmailAndPassword(String email, String password);
    Optional<AdminRegModel> findByEmail(String email);
}
