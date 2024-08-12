package com.example.ParlourApp.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Total bookings")
public class DashBoardController
{
    @Autowired
    DashBoardService dashBoardService;

   @GetMapping("/ids")
    public ResponseEntity<List<Long>>getAllBookingIds()
   {
       List<Long> bookingIds=dashBoardService.getAllBookingIds();
       return ResponseEntity.ok(bookingIds);
   }

   @GetMapping("/times")
    public ResponseEntity<List<LocalTime>>getAllBookingTimes()
   {
       List<LocalTime> bookingTimes=dashBoardService.getAllBookingTimes();
       return ResponseEntity.ok(bookingTimes);

   }
   @GetMapping("/prices")
    public ResponseEntity<List<BigDecimal>>getAllBookingPrices()
   {
       List<BigDecimal> bookingPrices=dashBoardService.getAllBookingPrices();
       return ResponseEntity.ok(bookingPrices);
   }
    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getAllBookingStatuses() {
        List<String> bookingStatuses = dashBoardService.getAllBookingStatuses();
        return ResponseEntity.ok(bookingStatuses);
    }

}
