package com.example.ParlourApp.userbilling;

import com.example.ParlourApp.cart.CartRegModel;
import com.example.ParlourApp.cart.CartRepository;
import com.example.ParlourApp.razorPay.OrderDetailsService;
import com.example.ParlourApp.razorPay.TransactionDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@Slf4j
public class UserBillingService {
    @Autowired
    private UserBillingRepository userBillingRepository;
    @Autowired
    OrderDetailsService orderDetailsService;

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


        if (discount != null) {
            totalPrice = totalPrice.subtract(discount);
        }

        UserBillingRegModel userBilling = new UserBillingRegModel();
        userBilling.setUserId(cartItems.get(0).getUserId());
        userBilling.setUniqueId(uniqueId);
        userBilling.setTotalPrice(totalPrice);
        userBilling.setStatus("Pending");
        TransactionDetails transactionDetails=orderDetailsService.createTransaction(totalPrice.intValue(), cartItems.get(0).getUserId());
        if(transactionDetails!=null)
                {
                    userBilling.setOrderId(transactionDetails.getOrderId());
                    userBilling.setPaymentId(transactionDetails.getPaymentId());

                }else {
            throw new RuntimeException("Failed to retrieve transaction details");
        }
        userBilling.setBookingDate(cartItems.get(0).getBookingDate());
        userBilling.setBookingTime(cartItems.get(0).getBookingTime());
        userBilling.setItemId(cartItems.get(0).getItemId());
        userBilling.setParlourId(cartItems.get(0).getParlourId());
        userBilling.setEmployeeId(cartItems.get(0).getEmployeeId());
        userBilling.setQuantity(cartItems.get(0).getQuantity());

        // Set other fields from cartItems if needed

        return userBillingRepository.save(userBilling);

    }

    public List<UserBillingRegModel> getUserBilling() {
        return userBillingRepository.findAll();
    }

//    public List<UserBillingRegModel> getUserBillingForLastMonth() {
//        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
//        return userBillingRepository.findAllByBookingDateAfter(oneMonthAgo.atStartOfDay(ZoneId.systemDefault().systemDefault()).toInstant());
    }
