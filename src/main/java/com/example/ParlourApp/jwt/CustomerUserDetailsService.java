package com.example.ParlourApp.jwt;

import com.example.ParlourApp.admin.AdminRegModel;
import com.example.ParlourApp.admin.AdminRepository;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import com.example.ParlourApp.user.UserRegModel;
import com.example.ParlourApp.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {
      @Autowired
    AdminRepository adminRepository;
    @Autowired
    ParlourRepository parlourRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}", userName);
        Optional<AdminRegModel> admin = adminRepository.findByEmail(userName);
        if (admin.isPresent()) {
            AdminRegModel adminRegModel = admin.get();
            log.info("Admin found with roles: {}", adminRegModel.getRoles());
            List<GrantedAuthority> authorities = getAuthorities(adminRegModel.getRoles());
            return new User(adminRegModel.getEmail(), adminRegModel.getPassword(), authorities);
        }
        Optional<ParlourRegModel> parlour = parlourRepository.findByEmail(userName);
        if (parlour.isPresent()) {
            ParlourRegModel parlourRegModel = parlour.get();
            log.info("Parlour found with email: {}", parlourRegModel.getEmail());

                List<GrantedAuthority> authorities = getAuthorities(parlourRegModel.getRoles());
                log.info("Authorities: {}", authorities);
                return new User(parlourRegModel.getEmail(), parlourRegModel.getPassword(), authorities);
            }

        Optional<UserRegModel> user = userRepository.findByPhoneNumber(userName);
        if (user.isPresent()) {
            UserRegModel userRegModel = user.get();
            log.info("User found with phone number: {}", userRegModel.getPhoneNumber());
            List<GrantedAuthority> authorities = getAuthorities(userRegModel.getRoles());
            log.info("Authorities: {}", authorities);
            return new User(userRegModel.getPhoneNumber(), userRegModel.getPassword(), authorities);
        }

       log.warn("User with identifier {} not found", userName);
        throw new UsernameNotFoundException("User with identifier " + userName + " not found");
    }
    private List<GrantedAuthority> getAuthorities(List<String> roles) {

        return roles.stream()
                .map(role->role.startsWith("ROLE_")?new SimpleGrantedAuthority(role):new SimpleGrantedAuthority("ROLE_" +role.toUpperCase()))
                .collect(Collectors.toList());
    }
}
