package com.example.ParlourApp.Rating;

import com.example.ParlourApp.parlour.ParlourRegModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "RATING")
public class RatingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "parlour_id", nullable = false)
    @JsonBackReference
    private ParlourRegModel parlour;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "RatingValue", nullable = false)
    private int ratingValue;

    @Column(name = "review", nullable = false)
    private String review;

    public Long getParlourId() {
        return parlour.getId();
    }

    public int getRatingValue() {
        return ratingValue;
    }
}

