package com.example.ParlourApp.userbilling;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface UserBillingRepository extends JpaRepository<UserBillingRegModel,Long>
{
    List<UserBillingRegModel> findByUserIdAndOrderIdAndPaymentIdAndItemIdAndParlourIdAndEmployeeIdAndBookingTimeAndBookingDateAndQuantityAndStatus(
            Long userId, String orderId, String paymentId, Long itemId, Long parlourId, Long employeeId, LocalTime bookingTime, LocalDate bookingDate, Integer quantity, String status
    );


    Optional<UserBillingRegModel> findByOrderId(String orderId);

    Optional<UserBillingRegModel> findById(Long userId);


    Optional<UserBillingRegModel> findByUserId(Long userId);

    List<UserBillingRegModel> findByUserIdAndBookingDate(Long userId, LocalDate bookingDate);

    Optional<UserBillingRegModel> findFirstByUserIdAndBookingDate(Long userId, LocalDate bookingDate);

}
