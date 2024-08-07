package com.example.ParlourApp.Rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingModel,Long> {
    List<RatingModel> findByParlour_Id(Long parlourId);
}
