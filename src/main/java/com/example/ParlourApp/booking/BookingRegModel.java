package com.example.ParlourApp.booking;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table (name ="booking ")
public class BookingRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "userId")
    private Long userId;

    @Column(name = "itemId")
    private Long itemId;

    @Column(name = "parlourId")
    private Long parlourId;

    @Column(name="employeeId")
    private Long employeeId;

    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "subCategoryId")
    private Long subCategoryId;

    @Column(name = "subSubCategoryId")
    private Long subSubCategoryId;

    @Column(name = "bookingDate")
    private LocalDate bookingDate;

    @Column(name = "bookingTime")
    private LocalTime bookingTime;





    }



