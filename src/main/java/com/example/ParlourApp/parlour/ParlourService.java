package com.example.ParlourApp.parlour;

import com.example.ParlourApp.admin.AdminRegModel;
import com.example.ParlourApp.dto.ParlourDetails;
import com.example.ParlourApp.employee.EmployeeRegModel;
import com.example.ParlourApp.employee.EmployeeRepository;
import com.example.ParlourApp.jwt.CustomerUserDetailsService;
import com.example.ParlourApp.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@Service
@Slf4j
public class ParlourService
{
    @Autowired
    private ParlourRepository parlourRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    public ParlourRegModel registerParlour(ParlourRegModel parlourRegModel) {
        parlourRegModel.setPassword(passwordEncoder.encode(parlourRegModel.getPassword()));
        parlourRegModel.getRoles().add("ROLE_PARLOUR");
        parlourRegModel.setStatus(0);
        return parlourRepository.save(parlourRegModel);
    }

    public  String authenticate(String email,String password)
    {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return jwtUtil.generateToken(userDetails);
        } else {
            return null;
        }
    }




    public ParlourRegModel getParlourByEmailAndPassword(String email, String password) {
        return parlourRepository.findByEmailAndPassword(email, password);
    }

    public ParlourRegModel getParlourById(Long id) {
        return parlourRepository.findById(id).orElseThrow(() -> new RuntimeException("Parlour not found with id: " + id));
    }

    public boolean updateParlour(Long id, ParlourRegModel parlourDetails) {
        Optional<ParlourRegModel> optionalParlour = parlourRepository.findById(id);

        if (optionalParlour.isPresent()) {
            ParlourRegModel existingParlour = optionalParlour.get();

            existingParlour.setParlourName(parlourDetails.getParlourName());
            existingParlour.setPhoneNumber(parlourDetails.getPhoneNumber());
            existingParlour.setPassword(parlourDetails.getPassword());
            existingParlour.setEmail(parlourDetails.getEmail());
            if (parlourDetails.getImage() != null) {
                existingParlour.setImage(parlourDetails.getImage());
            }
            existingParlour.setLicenseNumber(parlourDetails.getLicenseNumber());

            if (parlourDetails.getLicenseImage() != null) {
                existingParlour.setLicenseImage(parlourDetails.getLicenseImage());
            }
            existingParlour.setRatings(parlourDetails.getRatings());
            existingParlour.setLocation(parlourDetails.getLocation());
            existingParlour.setDescription(parlourDetails.getDescription());
            existingParlour.setStatus(0);

            parlourRepository.save(existingParlour);
            return true;
        } else {
            return false;
        }
    }

    public ParlourRegModel getParlourByName(String parlourName) {
        return parlourRepository.findByParlourName(parlourName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parlour name"));
    }

    public List<EmployeeRegModel> getEmployeesByParlourId(Long parlourId) {
        return employeeRepository.findByParlourId_Id(parlourId);
    }

    public ParlourDetails getParlourDetails(String parlourName) {
        ParlourRegModel parlour = getParlourByName(parlourName);
        List<EmployeeRegModel> employees = getEmployeesByParlourId(parlour.getId());
        return new ParlourDetails(parlour.getParlourName(), parlour.getPhoneNumber(), parlour.getEmail(), employees);
    }

}

