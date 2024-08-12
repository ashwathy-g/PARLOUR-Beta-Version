package com.example.ParlourApp.dto;

import com.example.ParlourApp.employee.EmployeeRegModel;
import com.example.ParlourApp.items.ItemRegModel;
import lombok.Data;

import java.util.List;

@Data
public class ParlourDetailsDTO
{
    private Long id;
    private String parlourName;
    private String phoneNumber;
    private String email;
    private byte[] image;
    private Integer ratings;
    private String location;
    private String description;
    private Integer status;
    private List<EmployeeDto> employees;
    private List<ItemDto> items;

}
