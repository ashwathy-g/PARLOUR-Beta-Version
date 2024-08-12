package com.example.ParlourApp.parlour;

import com.example.ParlourApp.dto.ParlourDetails;
import com.example.ParlourApp.dto.ParlourDetailsDTO;
import com.example.ParlourApp.dto.ParlourLogin;
import com.example.ParlourApp.dto.ParlourStatusResponse;
import com.example.ParlourApp.jwt.CustomerUserDetailsService;
import com.example.ParlourApp.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/parlour")
public class ParlourController {
    @Autowired
    private ParlourService parlourService;

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ParlourRepository parlourRepository;

    @PostMapping("/ParlourReg")
    public ResponseEntity<ParlourRegModel> registerParlour(@RequestParam("parlourName") String parlourName,
                                                           @RequestParam("phoneNumber") String phoneNumber,
                                                           @RequestParam("password") String password,
                                                           @RequestParam("email") String email,
                                                           @RequestParam("image") MultipartFile image,
                                                           @RequestParam("licenseNumber") Long licenseNumber,
                                                           @RequestParam("licenseImage") MultipartFile licenseImage,
                                                           @RequestParam("ratings") Integer ratings,
                                                           @RequestParam("location") String location,
                                                           @RequestParam("description") String description)throws IOException {
        ParlourRegModel parlourRegModel = new ParlourRegModel();
        parlourRegModel.setParlourName(parlourName);
        parlourRegModel.setPhoneNumber(phoneNumber);
        parlourRegModel.setPassword(passwordEncoder.encode(password));
        parlourRegModel.setEmail(email);
        try(InputStream imageInputStream=image.getInputStream())
        {
            byte[]imageBytes=imageInputStream.readAllBytes();
            parlourRegModel.setImage(image.getBytes());
        }

        parlourRegModel.setLicenseNumber(licenseNumber);
        try (InputStream licenseInputStream = licenseImage.getInputStream()) {
            byte[] licenseBytes = licenseInputStream.readAllBytes();
            parlourRegModel.setLicenseImage(licenseBytes);
        }

        parlourRegModel.setRatings(ratings);
        parlourRegModel.setLocation(location);
        parlourRegModel.setDescription(description);


        ParlourRegModel registeredParlour = parlourService.registerParlour(parlourRegModel);
        return ResponseEntity.ok(registeredParlour);
    }


    @PostMapping("/ParlourLogin")
    public ResponseEntity<Map<String, Object>> login(@RequestBody ParlourLogin parlourLogin) {
        String email = parlourLogin.getEmail();
        String password = parlourLogin.getPassword();

        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            String token = jwtUtil.generateToken(userDetails);

            Optional<ParlourRegModel> optionalParlour = parlourRepository.findByEmail(email);
            if (optionalParlour.isPresent()) {
                ParlourRegModel parlour = optionalParlour.get();
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("parlour", parlour);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Parlour not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }


    @GetMapping("/ParlourStatus/{parlourId}")
    public ResponseEntity<ParlourStatusResponse> getParlourStatus(@PathVariable Long parlourId) {
        ParlourRegModel parlour = parlourService.getParlourById(parlourId);
        if (parlour!= null) {

            String token = ""; // Replace with actual token retrieval logic
            String message = "Status Retrieved Successfully"; // Replace with appropriate message
            Integer status = parlour.getStatus(); // Assuming getStatus() returns Integer

            ParlourStatusResponse response = new ParlourStatusResponse(token, message, status);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateParlour(@PathVariable Long id,
                                                @RequestParam("parlourName") String parlourName,
                                                @RequestParam("phoneNumber") String phoneNumber,
                                                @RequestParam("password") String password,
                                                @RequestParam("email") String email,
                                                @RequestParam(value = "image", required = false) MultipartFile image,
                                                @RequestParam("licenseNumber") Long licenseNumber,
                                                @RequestParam(value = "licenseImage", required = false) MultipartFile licenseImage,
                                                @RequestParam("ratings") Integer ratings,
                                                @RequestParam("location") String location,
                                                @RequestParam("description") String description,
                                                @RequestParam("status") Integer status) throws IOException {
        ParlourRegModel parlourRegModel = new ParlourRegModel();
        parlourRegModel.setParlourName(parlourName);
        parlourRegModel.setPhoneNumber(phoneNumber);
        parlourRegModel.setPassword(password);
        parlourRegModel.setEmail(email);
        if (image != null) {
            try (InputStream imageInputStream = image.getInputStream()) {
                byte[] imageBytes = imageInputStream.readAllBytes();
                parlourRegModel.setImage(imageBytes);
            }
        }
        parlourRegModel.setLicenseNumber(licenseNumber);
        if (licenseImage != null) {
            try (InputStream licenseInputStream = licenseImage.getInputStream()) {
                byte[] licenseBytes = licenseInputStream.readAllBytes();
                parlourRegModel.setLicenseImage(licenseBytes);
            }
        }
        parlourRegModel.setRatings(ratings);
        parlourRegModel. setLocation(location);
        parlourRegModel.setDescription(description);
        parlourRegModel.setStatus(status);

        boolean isUpdated = parlourService.updateParlour(id, parlourRegModel);
        if (isUpdated) {
            return ResponseEntity.ok("Parlour details updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update parlour details");
        }
    }
      @GetMapping("/{id}")
      public ResponseEntity<List<ParlourDetailsDTO>>getParlourDetails(@PathVariable Long id){
        try {
           Optional<ParlourRegModel>parlourRegModelOptional=parlourRepository.findById(id);
           if (parlourRegModelOptional.isPresent()){
               return parlourService.getParlourAllDetails(id);
           }else {
               return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
      }
      @GetMapping("/getAllParlours" )
      public ResponseEntity<List<ParlourRegModel>> getAllParlours() {
     List<ParlourRegModel> parlours = parlourService.getAllParlours();
     return ResponseEntity.ok(parlours);
}
}



