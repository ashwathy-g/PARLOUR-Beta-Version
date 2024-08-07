package com.example.ParlourApp.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class SecurityConfig
{
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)

    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.cors(Customizer.withDefaults())
                .authorizeRequests(authorizeRequest ->authorizeRequest
                                .requestMatchers("/admin/AdminReg","/user/UserReg","/parlour/ParlourReg")
                                .permitAll()
                                .requestMatchers("/admin/AdminLogin").permitAll()
                                .requestMatchers("/parlour/ParlourLogin").permitAll()
                                .requestMatchers("/user/UserLogin").permitAll()
                                .requestMatchers("/Categories/add").hasRole("ADMIN")
                                .requestMatchers("/SubCategory/add_Sub").hasRole("ADMIN")
                                .requestMatchers("/SubSubCategory/add_Sub").hasRole("ADMIN")
                                .requestMatchers("/admin/approve/{id}").hasRole("ADMIN")
                                .requestMatchers("/Items/AddItems").hasRole("PARLOUR")
                                .requestMatchers("/Items/{itemId}").hasRole("PARLOUR")
                                .requestMatchers("/employees/addEmployee").hasRole("PARLOUR")
                                .requestMatchers("/offers/offer").hasRole("PARLOUR")
                                .requestMatchers("/offers/{id}").hasRole("PARLOUR")
                                .requestMatchers("/offers/{id}").hasRole("PARLOUR")
                                .requestMatchers("/offer-categories/offer").hasRole("PARLOUR")
                                .requestMatchers("/offer-categories/{id}").hasRole("PARLOUR")
                                .requestMatchers("/offer-categories/{id}").hasRole("PARLOUR")
                                .requestMatchers("/userBill/create").hasRole("PARLOUR")
                                .requestMatchers("/api/cart/add").hasRole("USER")
                                .requestMatchers("/orderDetails/createTransaction/{amount}/{userId}").hasRole("USER")
                                .requestMatchers("/verifypayment/payment").hasRole("USER")
                                .requestMatchers("/Items/getAllItems").permitAll()
                                .requestMatchers("/Items/{itemId}").permitAll()
                                .requestMatchers("/ratings/add").permitAll()
                                .anyRequest().authenticated())
                .csrf(csrf->csrf.disable());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
