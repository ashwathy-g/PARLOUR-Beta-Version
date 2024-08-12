package com.example.ParlourApp.razorPay.verifyPaymentID;

import com.example.ParlourApp.userbilling.UserBillingRegModel;
import com.example.ParlourApp.userbilling.UserBillingRepository;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping(path = "/verifyPayment")
public class VerifyPaymentController
{
    @Autowired
    UserBillingRepository userBillingRepository;

    @Autowired
    VerifyPaymentService verifyPaymentService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/payment")
    public ResponseEntity<Object> verifyPayment(@RequestParam String orderId,
                                                @RequestParam String paymentId,
                                                @RequestParam String signature) {
        try {
            boolean paymentVerified = verifyPaymentService.verifyPayment(orderId,paymentId, signature);
            if (paymentVerified) {
                Optional<UserBillingRegModel> optionalUserBillingRegModel = userBillingRepository.findByOrderId(orderId);
                if (optionalUserBillingRegModel.isPresent()) {
                    UserBillingRegModel userBillingRegModel = optionalUserBillingRegModel.get();
                    userBillingRegModel.setPaymentId(paymentId);
                    userBillingRepository.save(userBillingRegModel);

                    return ResponseEntity.ok("Payment Successful. Order Id: " + orderId + ", Payment Id: " + paymentId);
                } else {
                    return ResponseEntity.badRequest().body("Order not found: " + orderId);
                }
            } else {
                return ResponseEntity.badRequest().body("Payment verification failed.");
            }

        } catch (RazorpayException e) {
            // Handle specific Razorpay exceptions here (optional)
            return ResponseEntity.badRequest().body("Payment verification failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Server Error");
        }
    }
}
