package com.example.ParlourApp.parlour;

import com.example.ParlourApp.dto.Parlour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParlourRepository extends JpaRepository<ParlourRegModel,Long>
{

     Optional<ParlourRegModel> findByEmailAndPassword(String email, String password);

   Optional<ParlourRegModel>  findByEmail(String email);
    Optional<ParlourRegModel>findById(Long id);

    Optional<ParlourRegModel> findByParlourName(String parlourName);

    List<ParlourRegModel> findByRatingsIsNull();
   @Query("SELECT COUNT(p) FROM ParlourRegModel p WHERE p.active = true")
   Long countActiveParlours();
   Long countByActiveTrue();


}


