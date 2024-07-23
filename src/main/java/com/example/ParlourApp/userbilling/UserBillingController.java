package com.example.ParlourApp.userbilling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin
@RestController
@RequestMapping(path = "/userBill")

public class UserBillingController {
    @Autowired
    private UserBillingService userBillingService;


    @PostMapping("/create")
    public ResponseEntity<UserBillingRegModel> createUserBilling(@RequestParam String uniqueId, @RequestParam(required = false) BigDecimal discount) {
        UserBillingRegModel userBilling = userBillingService.createUserBilling(uniqueId, discount);
        return ResponseEntity.ok(userBilling);
    }
}