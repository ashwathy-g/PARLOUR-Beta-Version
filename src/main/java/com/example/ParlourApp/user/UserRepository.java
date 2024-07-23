package com.example.ParlourApp.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserRegModel,Integer>{


    Optional<UserRegModel> findFirstByFullName(String fullName);


    boolean existsByEmail(String email);

  Optional< UserRegModel>  findByPhoneNumber(String phoneNumber);
}
