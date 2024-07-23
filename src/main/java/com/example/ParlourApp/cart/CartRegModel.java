package com.example.ParlourApp.cart;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@CrossOrigin
@Data
@Entity
@Table(name = "cart")
public class CartRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "itemId")
    private Long itemId;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "actualPrice")
    private BigDecimal actualPrice;

    @Column(name = "parlourId")
    private Long parlourId;

    @Column(name = "parlourName")
    private String parlourName;

    @Column(name = "employeeId")
    private Long employeeId;

    @Column(name = "employeeName")
    private String employeeName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "uniqueId")
    private String uniqueId;

    @Column(name = "bookingDate")
    private LocalDate bookingDate;

    @Column(name="bookingTime")
    private LocalTime bookingTime;


}
