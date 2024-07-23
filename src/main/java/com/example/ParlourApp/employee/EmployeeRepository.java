package com.example.ParlourApp.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeRegModel,Long>
{
    @Query("SELECT e.employeeName FROM EmployeeRegModel e JOIN e.parlourId p WHERE p.parlourName = :parlourName")
    List<String> findEmployeeNamesByParlourName(@Param( "parlourName") String parlourName);

    Optional<EmployeeRegModel> findByEmployeeName(String employeeName);

    List<EmployeeRegModel> findByParlourId_Id(Long parlourId);
    List<EmployeeRegModel> findByParlourId_IdAndIsAvailableTrue(Long parlourId);


}
