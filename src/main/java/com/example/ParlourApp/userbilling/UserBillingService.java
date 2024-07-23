package com.example.ParlourApp.userbilling;

import com.example.ParlourApp.cart.CartRegModel;
import com.example.ParlourApp.cart.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class UserBillingService {
    @Autowired
    private UserBillingRepository userBillingRepository;

    @Autowired
    CartRepository cartRepository;


    public UserBillingRegModel createUserBilling(String uniqueId, BigDecimal discount) {
        List<CartRegModel> cartItems = cartRepository.findAllByUniqueId(uniqueId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("No cart items found for the given unique ID");
        }

        BigDecimal totalPrice = cartItems.stream()
                .map(CartRegModel::getActualPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Apply discount if any
        if (discount != null) {
            totalPrice = totalPrice.subtract(discount);
        }

        UserBillingRegModel userBilling = new UserBillingRegModel();
        userBilling.setUserId(cartItems.get(0).getUserId());
        userBilling.setUniqueId(uniqueId);
        userBilling.setTotalPrice(totalPrice);
        userBilling.setStatus("Pending");

        // Set other fields from cartItems if needed

        return userBillingRepository.save(userBilling);

    }
}