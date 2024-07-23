package com.example.ParlourApp.parlour;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParlourRepository extends JpaRepository<ParlourRegModel,Long>
{

    ParlourRegModel findByEmailAndPassword(String email, String password);

   Optional<ParlourRegModel>  findByEmail(String email);
    Optional<ParlourRegModel>findById(Long id);

    Optional<ParlourRegModel> findByParlourName(String parlourName);


    boolean existsByEmail(String email);
}
