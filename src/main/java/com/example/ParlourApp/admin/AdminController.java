package com.example.ParlourApp.admin;

import com.example.ParlourApp.jwt.CustomerUserDetailsService;
import com.example.ParlourApp.jwt.JwtUtil;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/admin")
@Slf4j
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(path = "/AdminReg")


    public Boolean register(@RequestBody AdminRegModel adminRegModel) {

        AdminRegModel newAdminRegModel = new AdminRegModel();
        newAdminRegModel.setAdminName(adminRegModel.getAdminName());
        newAdminRegModel.setPassword(passwordEncoder.encode(adminRegModel.getPassword()));
        newAdminRegModel.setEmail(adminRegModel.getEmail());
        newAdminRegModel.setRoles(Arrays.asList("ROLE_ADMIN"));
        log.info("Roles assigned to admin during registration: {}", newAdminRegModel.getRoles());
        AdminRegModel registeredAdmin = adminService.registerAdmin(adminRegModel.getAdminName(), adminRegModel.getPassword(), adminRegModel.getEmail(), newAdminRegModel.getRoles());
        return registeredAdmin == null;
    }

    @PostMapping(path = "/AdminLogin")
    public ResponseEntity<String> login(@RequestBody AdminRegModel adminRegModel) {
        log.info("Admin login attempt: {}", adminRegModel.getEmail());
        String userName = adminRegModel.getEmail();
        String password = adminRegModel.getPassword();

        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            String token = jwtUtil.generateToken(userDetails);
            log.info("Admin login successful: {}", adminRegModel.getEmail());
            return ResponseEntity.ok(token);
        } else {
            log.warn("Admin login failed: {}", adminRegModel.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @GetMapping("/allRegisteredParlour")
    public ResponseEntity<List<ParlourRegModel>> getAllRegisteredParlours() {
        List<ParlourRegModel> parlours = adminService.getAllRegisteredParlours();
        if (!parlours.isEmpty()) {
            return ResponseEntity.ok(parlours);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveParlour(@PathVariable Long id, @RequestParam("status") Integer status,
                                                 @RequestHeader(name = "Authorization") String token) {
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            boolean isApproved = adminService.approveParlour(id, status);
            if (isApproved) {
                return ResponseEntity.ok("Parlour approved successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to approve parlour");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
    }
}








