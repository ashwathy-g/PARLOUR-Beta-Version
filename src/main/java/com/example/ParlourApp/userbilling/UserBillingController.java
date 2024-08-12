package com.example.ParlourApp.userbilling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

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
    @GetMapping("/{id}")
    public ResponseEntity<UserBillingRegModel> getUserBillingById(@PathVariable Long id) {
        Optional<UserBillingRegModel> userBilling = userBillingService.getUserBillingById(id);
        return userBilling.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}