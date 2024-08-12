package com.example.ParlourApp.dashboard;

import com.example.ParlourApp.booking.BookingRepository;
import com.example.ParlourApp.cart.CartRepository;
import com.example.ParlourApp.userbilling.UserBillingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Service
@Slf4j
public class DashBoardService
{
  @Autowired
  CartRepository cartRepository;
  @Autowired
  UserBillingRepository userBillingRepository;
  public List<Long>getAllBookingIds()
  {
      return cartRepository.findAllBookingIds();
  }
  public List<LocalTime> getAllBookingTimes() {
    return cartRepository.findAllBookingTimes();
  }

  public List<BigDecimal> getAllBookingPrices() {
    return cartRepository.findAllBookingPrices();
  }

  public List<String> getAllBookingStatuses() {
    return userBillingRepository.findAllBookingStatuses();
  }
}
