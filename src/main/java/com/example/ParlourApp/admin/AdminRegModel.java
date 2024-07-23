package com.example.ParlourApp.admin;

import com.example.ParlourApp.jwt.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "ADMIN")
public class AdminRegModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "adminName")
    private String adminName;

    @Column(name = "password")
    private String password;


    @Column(name = "email")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "admin_roles",joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "role")
    private List<String>roles;

    @Transient
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }


}





