package com.example.ParlourApp.admin;

import com.example.ParlourApp.jwt.CustomerUserDetailsService;
import com.example.ParlourApp.jwt.JwtFilter;
import com.example.ParlourApp.jwt.JwtUtil;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@CrossOrigin
@Service
@Slf4j
public class AdminService {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ParlourRepository parlourRepository;

    public AdminRegModel registerAdmin(String adminName, String password, String email, List<String> roles) {


        AdminRegModel adminRegModel = new AdminRegModel();
        adminRegModel.setAdminName(adminName);
        adminRegModel.setPassword(password);
        adminRegModel.setEmail(email);
        adminRegModel.setRoles(roles);
        return adminRepository.save(adminRegModel);

    }
    public String authenticate(String email, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return jwtUtil.generateToken(userDetails);
        } else {
            return null;
        }
    }
    public AdminRegModel findByEmailAndPassword(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password).orElse(null);
    }
    private AdminRegModel getAdminRegModelFromUser(User user) {
        return adminRepository.findByEmail(user.getUsername()).orElse(null);

    }
    public List<ParlourRegModel> getAllRegisteredParlours() {
        return parlourRepository.findAll();
    }
    public boolean approveParlour(Long id, Integer status) {
        Optional<ParlourRegModel> optionalParlour = parlourRepository.findById(id);

        if (optionalParlour.isPresent()) {
            ParlourRegModel existingParlour = optionalParlour.get();
            existingParlour.setStatus(status); // Set the status to the provided status

            parlourRepository.save(existingParlour);
            return true;
        } else {
            return false;
        }
    }
}





