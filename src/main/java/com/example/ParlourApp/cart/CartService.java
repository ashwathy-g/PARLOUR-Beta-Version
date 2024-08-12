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
            validateAndPopulateCartItem(cartItem);
            cartItem.setUniqueId(generateUniqueId(cartItem.getUserId()));
            BigDecimal totalPrice = cartItem.getActualPrice().multiply(new BigDecimal(cartItem.getQuantity()));
            cartItem.setActualPrice(totalPrice);

            log.info("Saving cart item: {}", cartItem);
            addedItems.add(cartRepository.save(cartItem));

            UserBillingRegModel userBilling = createUserBilling(cartItem, totalPrice);
            log.info("Saving UserBillingRegModel: {}", userBilling);
            userBillingRepository.save(userBilling);

        }

        return addedItems;
    }

    private void validateAndPopulateCartItem(CartRegModel cartItem) {
        if (cartItem.getItemId() == null) {
            log.error("Item ID is null for cartItem: {}", cartItem);
            throw new IllegalArgumentException("Item ID must not be null");
        } else {
            itemRepository.findById(cartItem.getItemId()).ifPresentOrElse(item -> {
                cartItem.setItemName(item.getItemName());
                cartItem.setActualPrice(item.getPrice());
            }, () -> {
                log.error("Item not found for ID: {}", cartItem.getItemId());
                throw new IllegalArgumentException("Item not found");
            });
        }

        if (cartItem.getParlourId() == null) {
            log.error("Parlour ID is null for cartItem: {}", cartItem);
            throw new IllegalArgumentException("Parlour ID must not be null");
        } else {
            parlourRepository.findById(cartItem.getParlourId()).ifPresentOrElse(parlour -> {
                cartItem.setParlourName(parlour.getParlourName());
            }, () -> {
                log.error("Parlour not found for ID: {}", cartItem.getParlourId());
                throw new IllegalArgumentException("Parlour not found");
            });
        }

        if (cartItem.getEmployeeId() == null) {
            log.error("Employee ID is null for cartItem: {}", cartItem);
            throw new IllegalArgumentException("Employee ID must not be null");
        } else {
            employeeRepository.findById(cartItem.getEmployeeId()).ifPresentOrElse(employee -> {
                cartItem.setEmployeeName(employee.getEmployeeName());
            }, () -> {
                log.error("Employee not found for ID: {}", cartItem.getEmployeeId());
                throw new IllegalArgumentException("Employee not found");
            });
        }
    }

    private UserBillingRegModel createUserBilling(CartRegModel cartItem, BigDecimal totalPrice) {
        UserBillingRegModel userBilling = new UserBillingRegModel();
        userBilling.setUserId(cartItem.getUserId());
        userBilling.setItemId(cartItem.getItemId());
        userBilling.setParlourId(cartItem.getParlourId());
        userBilling.setEmployeeId(cartItem.getEmployeeId());
        userBilling.setBookingDate(cartItem.getBookingDate());
        userBilling.setBookingTime(cartItem.getBookingTime());
        userBilling.setQuantity(cartItem.getQuantity());
        userBilling.setTotalPrice(totalPrice);
        userBilling.setUniqueId(cartItem.getUniqueId());
        userBilling.setStatus("Pending");
        return userBilling;
    }
    private String generateUniqueId(Long userId) {
        return UUID.nameUUIDFromBytes(userId.toString().getBytes()).toString();
    }
    public List<Double> getAllBookingPrices()
    {
        return null;
    }
}



//        for (CartRegModel cartItem : cartItems) {
//            if (cartItem.getItemId()!=null) {
//                itemRepository.findById(cartItem.getItemId()).ifPresentOrElse(item -> {
//                    cartItem.setItemName(item.getItemName());
//                    cartItem.setActualPrice(item.getPrice());
//                }, () -> {
//                    log.error("Item not found for ID: {}", cartItem.getItemId());
//                    throw new IllegalArgumentException("Item not found");
//                });
//            }
//            else
//            {
//                log.error("Item ID is null for cartItem: {}", cartItem);
//                throw new IllegalArgumentException("Item ID must not be null");
//
//            }
//            if (cartItem.getParlourId()!=null) {
//
//                parlourRepository.findById(cartItem.getParlourId()).ifPresentOrElse(parlour -> {
//                    cartItem.setParlourName(parlour.getParlourName());
//                },() -> {
//                    log.error("Parlour not found for ID: {}", cartItem.getParlourId());
//                    throw new IllegalArgumentException("Parlour not found");
//                });
//            }
//            else {
//                log.error("Parlour ID is null for cartItem: {}", cartItem);
//                throw new IllegalArgumentException("Parlour ID must not be null");
//            }
//            if (cartItem.getEmployeeId()!=null) {
//
//                employeeRepository.findById(cartItem.getEmployeeId()).ifPresentOrElse(employee -> {
//                    cartItem.setEmployeeName(employee.getEmployeeName());
//                }, () -> {
//                    log.error("Employee not found for ID: {}", cartItem.getEmployeeId());
//                    throw new IllegalArgumentException("Employee not found");
//                });
//            }
//            else {
//                log.error("Employee ID is null for cartItem: {}", cartItem);
//                throw new IllegalArgumentException("Employee ID must not be null");
//            }
//            cartItem.setUniqueId(generateUniqueId(cartItem.getUserId()));
//
//            BigDecimal totalPrice = cartItem.getActualPrice().multiply(new BigDecimal(cartItem.getQuantity()));
//            cartItem.setActualPrice(totalPrice);
//
//            addedItems.add(cartRepository.save(cartItem));
//
//
//            UserBillingRegModel userBilling = new UserBillingRegModel();
//            userBilling.setUserId(cartItem.getUserId());
//            userBilling.setItemId(cartItem.getItemId());
//            userBilling.setParlourId(cartItem.getParlourId());
//            userBilling.setEmployeeId(cartItem.getEmployeeId());
//            userBilling.setBookingDate(cartItem.getBookingDate());
//            userBilling.setBookingTime(cartItem.getBookingTime());
//            userBilling.setQuantity(cartItem.getQuantity());
//            log.info("Saving UserBillingRegModel: {}", userBilling);
//
//            userBillingRepository.save(userBilling);
//        }
//
//        return addedItems;
//
//    }



