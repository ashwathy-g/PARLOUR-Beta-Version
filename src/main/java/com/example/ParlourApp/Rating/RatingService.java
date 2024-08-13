package com.example.ParlourApp.Rating;

import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RatingService {
    private final RatingRepository ratingRepository;
    private final ParlourService parlourService;
    @Lazy
    @Autowired
    public RatingService(@Lazy RatingRepository ratingRepository, ParlourService parlourService) {
        this.ratingRepository = ratingRepository;
        this.parlourService = parlourService;
    }

    public RatingModel addRating(Long parlourId, String customerName, int ratingValue, String review) {
        ParlourRegModel parlour = parlourService.getParlourById(parlourId);
        if (parlour == null) {
            throw new RuntimeException("Parlour not found with id " + parlourId);
        }

        RatingModel rating = new RatingModel();
        rating.setParlour(parlour);
        rating.setCustomerName(customerName);
        rating.setRatingValue(ratingValue);
        rating.setReview(review);
        return ratingRepository.save(rating);
    }

    public List<RatingModel> getRatingsByParlourId(Long parlourId) {
        return ratingRepository.findByParlour_Id(parlourId);
    }

    public List<ParlourRegModel> getParloursByRating(int ratingValue) {
        return ratingRepository.findAll().stream()
                .filter(rating -> rating.getRatingValue() == ratingValue)
                .map(RatingModel::getParlour)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<ParlourRegModel> getParloursByReviews() {
        return ratingRepository.findAll().stream()
                .collect(Collectors.groupingBy(RatingModel::getParlour, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<ParlourRegModel, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<ParlourRegModel> getUnratedParlours() {
        List<Long> ratedParlourIds = ratingRepository.findAll().stream()
                .map(RatingModel::getParlourId)
                .distinct()
                .collect(Collectors.toList());

        return parlourService.getAllParlours().stream()
                .filter(parlour -> !ratedParlourIds.contains(parlour.getId()))
                .collect(Collectors.toList());
    }


}
