package com.example.ParlourApp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class ItemDto
{
    private Long id;
    private String ItemName;
    private byte[] ItemImage;
    private Long CategoryId;
    private Long SubCategoryId;
    private Long SubSubCategoryId;
    private BigDecimal price;
    private Boolean Availability;
    private String Description;
    private LocalTime ServiceTime;

}
