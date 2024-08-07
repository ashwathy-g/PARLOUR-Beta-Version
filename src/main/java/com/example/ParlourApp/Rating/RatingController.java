package com.example.ParlourApp.Rating;

import com.example.ParlourApp.dto.RatingRequestDTO;
import com.example.ParlourApp.parlour.ParlourRegModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addRating(
            @RequestParam("parlourId") Long parlourId,
            @RequestBody RatingRequestDTO ratingRequest) {
        try {
            RatingModel rating = ratingService.addRating(
                    parlourId,
                    ratingRequest.getCustomerName(),
                    ratingRequest.getRatingValue(),
                    ratingRequest.getReview()
            );
            return ResponseEntity.ok(rating);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add rating: ");
        }
    }

        @GetMapping("/parlour/{parlourId}")
        public ResponseEntity<Object> getRatingsByParlour (@PathVariable Long parlourId){
            try {
                List<RatingModel> ratings = ratingService.getRatingsByParlourId(parlourId);
                return ResponseEntity.ok(ratings);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error getting ratings: " + e.getMessage());
            }
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

    // New endpoint to list parlours by the number of reviews
    @GetMapping("/parlours-by-reviews")
    public ResponseEntity<Object> getParloursByReviews() {
        try {
            List<ParlourRegModel> parlours = ratingService.getParloursByReviews();
            return ResponseEntity.ok(parlours);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving parlours by reviews: " + e.getMessage());
        }
    }

    // New endpoint to list unrated parlours
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
