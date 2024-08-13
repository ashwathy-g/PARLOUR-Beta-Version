package com.example.ParlourApp.razorPay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orderDetails")
public class OrderDetailController
{
    @Autowired
    private OrderDetailsService orderDetailService;


    @PreAuthorize("hasRole('USER')")
//    (origins = "http://localhost:8080")
    @CrossOrigin
    @GetMapping("/createTransaction/{amount}/{userId}")
    public ResponseEntity<TransactionDetails> createTransaction(@PathVariable Integer amount, @PathVariable Long userId){
        TransactionDetails transactionDetails = orderDetailService.createTransaction(amount,userId);
        if (transactionDetails != null){
            return new ResponseEntity<>(transactionDetails, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
