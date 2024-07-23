package com.example.ParlourApp.dto;

import com.example.ParlourApp.employee.EmployeeRegModel;
import lombok.Data;

import java.util.List;

@Data
public class ParlourDetails
{
    private String parlourName;
    private String phoneNumber;
    private String email;
    private List<EmployeeRegModel> employees;

    public ParlourDetails(String parlourName, String phoneNumber, String email, List<EmployeeRegModel> employees)
    {
        this.parlourName = parlourName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.employees = employees;
    }
}
