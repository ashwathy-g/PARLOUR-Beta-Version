package com.example.ParlourApp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Arrays;
import java.util.Optional;

@CrossOrigin
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserRegModel registerUser(String fullName,String password,String email,String phoneNumber)
    {
        if (fullName==null||password==null||email==null||phoneNumber==null)
        {
            return null;
        }else {
            if (userRepository.findFirstByFullName(fullName).isPresent())
            {
                System.out.println("Duplicate login");
                return null;
            }
            UserRegModel userRegModel=new UserRegModel();
            userRegModel.setFullName(fullName);
            userRegModel.setPassword(passwordEncoder.encode(password));
            userRegModel.setEmail(email);
            userRegModel.setPhoneNumber(phoneNumber);
            userRegModel.setRoles(Arrays.asList("ROLE_USER"));
            return userRepository.save(userRegModel);
        }
    }

    public UserRegModel authenticate(String phoneNumber, String password) {
        Optional<UserRegModel> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isPresent()) {
            UserRegModel user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}


