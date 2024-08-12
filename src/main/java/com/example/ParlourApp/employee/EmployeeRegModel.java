package com.example.ParlourApp.employee;

import com.example.ParlourApp.parlour.ParlourRegModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "employee")
public class EmployeeRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parlour_id")
    private ParlourRegModel parlourId;


    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "image",columnDefinition = "bytea")
    private byte[] image;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @ElementCollection
    @CollectionTable(name = "employee_availability", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "available_time_slot")
    private List<String> availableTimeSlots;





    public EmployeeRegModel(String employeeName, ParlourRegModel parlour) {
        this.employeeName = employeeName;
        this.parlourId = parlour;
    }
    public EmployeeRegModel(){

    }

}
