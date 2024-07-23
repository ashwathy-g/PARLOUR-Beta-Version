package com.example.ParlourApp.OfferCategory;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "OfferCategories")
public class OfferCategoryRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "categoryName")
    private String categoryName;

    @Column(name = "name")
    private String name;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "offerPrice")
    private Integer offerPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "image",columnDefinition = "bytea")
    private byte[]image;

    public String getDescription() {
        return description != null ? description : "No data";
    }

}
