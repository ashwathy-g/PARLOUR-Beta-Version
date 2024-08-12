package com.example.ParlourApp.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartRegModel,Long>
{
    CartRegModel findByUserId(Long userId);


    List<CartRegModel> findAllByUniqueId(String uniqueId);
    @Query("SELECT c.id FROM CartRegModel c")
    List<Long> findAllBookingIds();

    @Query("SELECT c.bookingTime FROM CartRegModel c")
    List<LocalTime> findAllBookingTimes();


    @Query("SELECT c.actualPrice FROM CartRegModel c")
    List<BigDecimal> findAllBookingPrices();

}
