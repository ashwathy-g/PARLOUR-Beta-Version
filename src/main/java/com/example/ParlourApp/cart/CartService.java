package com.example.ParlourApp.cart;

import com.example.ParlourApp.employee.EmployeeRepository;
import com.example.ParlourApp.items.ItemRepository;
import com.example.ParlourApp.parlour.ParlourRepository;
import com.example.ParlourApp.userbilling.UserBillingRegModel;
import com.example.ParlourApp.userbilling.UserBillingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CartService
{
    private static final String SESSION_ATTRIBUTE_UNIQUE_ID = "uniqueId";
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ParlourRepository parlourRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserBillingRepository userBillingRepository;
    public List<CartRegModel> addItemToCart(List<CartRegModel> cartItems) {
        List<CartRegModel> addedItems = new ArrayList<>();

        for (CartRegModel cartItem : cartItems) {
            if (cartItem.getItemId()!=null) {
                itemRepository.findById(cartItem.getItemId()).ifPresent(item -> {
                    cartItem.setItemName(item.getItemName());
                    cartItem.setActualPrice(item.getPrice());
                });
            }
            else
            {
                log.error("Item ID is null for cartItem: {}", cartItem);
                throw new IllegalArgumentException("Item ID must not be null");

            }
            if (cartItem.getParlourId()!=null) {

                parlourRepository.findById(cartItem.getParlourId()).ifPresent(parlour -> {
                    cartItem.setParlourName(parlour.getParlourName());
                });
            }
            else {
                log.error("Parlour ID is null for cartItem: {}", cartItem);
                throw new IllegalArgumentException("Parlour ID must not be null");
            }
            if (cartItem.getEmployeeId()!=null) {

                employeeRepository.findById(cartItem.getEmployeeId()).ifPresent(employee -> {
                    cartItem.setEmployeeName(employee.getEmployeeName());
                });
            }
            else {
                log.error("Employee ID is null for cartItem: {}", cartItem);
                throw new IllegalArgumentException("Employee ID must not be null");
            }
            cartItem.setUniqueId(generateUniqueId(cartItem.getUserId()));

            BigDecimal totalPrice = cartItem.getActualPrice().multiply(new BigDecimal(cartItem.getQuantity()));
            cartItem.setActualPrice(totalPrice);

            addedItems.add(cartRepository.save(cartItem));


            UserBillingRegModel userBilling = new UserBillingRegModel();
            userBilling.setUserId(cartItem.getUserId());
            userBilling.setItemId(cartItem.getItemId());
            userBilling.setParlourId(cartItem.getParlourId());
            userBilling.setEmployeeId(cartItem.getEmployeeId());
            userBilling.setBookingDate(cartItem.getBookingDate());
            userBilling.setBookingTime(cartItem.getBookingTime());
            userBilling.setQuantity(cartItem.getQuantity());

            userBillingRepository.save(userBilling);

        }

        return addedItems;

    }

    private String generateUniqueId(Long userId) {
        return UUID.nameUUIDFromBytes(userId.toString().getBytes()).toString();
    }
}


