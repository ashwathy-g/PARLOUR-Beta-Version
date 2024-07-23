package com.example.ParlourApp.booking;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/bookings")
public class BookingController
{
    @Autowired
    private BookingService bookingService;

    @GetMapping("/day/{parlourId}/{date}")
    public ResponseEntity<List<BookingRegModel>> getOneDayBookings(
            @PathVariable Long parlourId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<BookingRegModel> bookings = bookingService.getOneDayBookings(parlourId, date);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/week/{parlourId}/{date}")
    public ResponseEntity<List<BookingRegModel>> getOneWeekBookings(
            @PathVariable Long parlourId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<BookingRegModel> bookings = bookingService.getOneWeekBookings(parlourId, date);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/month/{parlourId}/{date}")
    public ResponseEntity<List<BookingRegModel>> getOneMonthBookings(
            @PathVariable Long parlourId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<BookingRegModel> bookings = bookingService.getOneMonthBookings(parlourId, date);
        return ResponseEntity.ok(bookings);
    }
}
