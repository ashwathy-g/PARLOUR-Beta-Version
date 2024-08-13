package com.example.ParlourApp.Dashboard;

import com.example.ParlourApp.Rating.RatingService;
import com.example.ParlourApp.dto.DashboardData;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.userbilling.UserBillingRegModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/DashBoard")
public class DashBoardController {
    @Autowired
     DashBoardService dashBoardService;

//    @Autowired
//    UserBillingRegModel userBillingRegModel;
    @Autowired
    RatingService ratingService;

    @GetMapping("/ids")
    public ResponseEntity<List<Long>> getAllBookingIds() {
        List<Long> bookingIds = dashBoardService.getAllBookingIds();
        return ResponseEntity.ok(bookingIds);
    }

    @GetMapping("/times")
    public ResponseEntity<List<LocalTime>> getAllBookingTimes() {
        List<LocalTime> bookingTimes = dashBoardService.getAllBookingTimes();
        return ResponseEntity.ok(bookingTimes);

    }

    @GetMapping("/prices")
    public ResponseEntity<List<BigDecimal>> getAllBookingPrices() {
        List<BigDecimal> bookingPrices = dashBoardService.getAllBookingPrices();
        return ResponseEntity.ok(bookingPrices);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getAllBookingStatuses() {
        List<String> bookingStatuses = dashBoardService.getAllBookingStatuses();
        return ResponseEntity.ok(bookingStatuses);
    }


        @GetMapping(path = "/alldata")
    public ResponseEntity<DashboardData> getDashboardData() {
       DashboardData dashboardData = dashBoardService.getDashboardData();
       return ResponseEntity.ok(dashboardData);
    }


    @GetMapping("/parlours-by-rating")
    public ResponseEntity<Object> getParloursByRating(@RequestParam("ratingValue") int ratingValue) {
        try {
            List<ParlourRegModel> parlours = ratingService.getParloursByRating(ratingValue);
            return ResponseEntity.ok(parlours);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving parlours by rating: " + e.getMessage());
        }
    }


    @GetMapping("/parlours-by-reviews")
    public ResponseEntity<Object> getParloursByReviews() {
        try {
            List<ParlourRegModel> parlours = ratingService.getParloursByReviews();
            return ResponseEntity.ok(parlours);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving parlours by reviews: " + e.getMessage());
        }
    }


    @GetMapping("/unrated-parlours")
    public ResponseEntity<Object> getUnratedParlours() {
        try {
            List<ParlourRegModel> unratedParlours = ratingService.getUnratedParlours();
            return ResponseEntity.ok(unratedParlours);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving unrated parlours: " + e.getMessage());

        }
    }
}