package com.example.ParlourApp.parlour;

import com.example.ParlourApp.employee.EmployeeRegModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Data
@Entity
@Table(name = "PARLOUR")
public class ParlourRegModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parlourName")
    private String parlourName;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @OneToMany(mappedBy = "parlourId", fetch = FetchType.EAGER)
    @JsonBackReference(value = "parlour-employee")
    private List<EmployeeRegModel> employees;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "image", columnDefinition = "bytea")
    private byte[] image;

    @Column(name = "licenseNumber")
    private Long licenseNumber;

    @Column(name = "licenseImage")
    private byte[] licenseImage;

    @Column(name = "ratings")
    private Integer ratings = 0;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "active",nullable = false)
    private Boolean active =true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "parlour_roles", joinColumns = @JoinColumn(name = "parlour_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();


    public ParlourRegModel() {
        this.ratings = 0;
        this.roles.add("ROLE_PARLOUR");
    }

    // Constructor with parameters
    public ParlourRegModel(Long id, String parlourName, String phoneNumber, List<EmployeeRegModel> employees,
                           String password, String email, byte[] image, Long licenseNumber, byte[] licenseImage,
                           Integer ratings, String location, String description, Double latitude, Double longitude,
                           Integer status, List<String> roles) {
        this.id = id;
        this.parlourName = parlourName;
        this.phoneNumber = phoneNumber;
        this.employees = employees;
        this.password = password;
        this.email = email;
        this.image = image;
        this.licenseNumber = licenseNumber;
        this.licenseImage = licenseImage;
        this.ratings = ratings;
        this.location = location;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.roles = roles;
    }

    public Boolean getActive() {
       return active;
    }

    public void setActive(Boolean active) {
       this.active = active;
    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }
}
