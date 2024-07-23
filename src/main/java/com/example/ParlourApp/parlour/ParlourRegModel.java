package com.example.ParlourApp.parlour;

import com.example.ParlourApp.employee.EmployeeRegModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Data

@Entity
@Table(name = "PARLOUR")

public class  ParlourRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parlourName")
    private String parlourName;

    @Column(name = "phoneNumber")
    private String phoneNumber;


    @Column(name = "password")
    private String password;


    @Column(name = "email")
    private String email;

    @Column(name = "image",columnDefinition = "bytea")
    private byte[]image;

    @Column(name = "licenseNumber")
    private Long licenseNumber;

    @Column(name="licenseImage")
    private byte[]licenseImage;


    @Column(name = "ratings")
    private Integer ratings=0;

    @Column(name="location")
    private String location;

    @Column(name="description")
    private String description;


    @Column(name="status",nullable = false)
    private Integer status;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "parlour_roles", joinColumns = @JoinColumn(name = "parlour_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();



    public ParlourRegModel() {
        this.ratings = 0;
        this.roles.add("ROLE_PARLOUR");
    }


       public ParlourRegModel(Long id, String parlourName, String phoneNumber, String password, String email, byte[] image, Long licenseNumber, byte[] licenseImage, String location, String description, Integer status,List<String>roles) {
        this.id = id;
        this.parlourName = parlourName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.image = image;
        this.licenseNumber = licenseNumber;
        this.licenseImage = licenseImage;
        this.location = location;
        this.description = description;
        this.status = status;
        this.ratings = 0;
        this.roles=roles;

    }


}
