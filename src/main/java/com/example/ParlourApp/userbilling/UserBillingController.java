package com.example.ParlourApp.userbilling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/userBill")
public class UserBillingController {

    @Autowired
    private UserBillingService userBillingService;

    // Creating a new user billing entry
    @PostMapping("/create")
    public ResponseEntity<UserBillingRegModel> createUserBilling(
            @RequestParam String uniqueId,
            @RequestParam(required = false) String discount) {

        // Convert discount from String to BigDecimal (handling possible format issues)
        BigDecimal discountValue = discount != null ? new BigDecimal(discount) : null;

        UserBillingRegModel userBilling = userBillingService.createUserBilling(uniqueId, discountValue);
        return ResponseEntity.ok(userBilling);
    }

    // Retrieving all user billing entries
    @GetMapping("/userBilling")
    public ResponseEntity<List<UserBillingRegModel>> getUserBilling() {
        List<UserBillingRegModel> billings = userBillingService.getUserBilling();
        return ResponseEntity.ok(billings);
    }

    // Retrieving user billing entries for the last month
//    @GetMapping("/userBillingLastMonth")
//    public ResponseEntity<List<UserBillingRegModel>> getUserBillingForLastMonth() {
//        List<UserBillingRegModel> lastMonthBillings = userBillingService.getUserBillingForLastMonth();
//        return ResponseEntity.ok(lastMonthBillings);
    }

