package com.example.ParlourApp.items;

import com.example.ParlourApp.category.CategoryRegModel;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.subcategory.SubCategoryRegModel;
import com.example.ParlourApp.subsubcategory.SubSubCategoryRegModel;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "Items")
public class ItemRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "ItemName")
    private String ItemName ;


    @Column(name = "ItemImage")
    private byte[]ItemImage;


    @Column(name = "Price")
    private BigDecimal Price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category_id",nullable = false)
    private CategoryRegModel category;

    @ManyToOne
    @JoinColumn(name = "Sub_Category_id",nullable = false)
    private SubCategoryRegModel subCategory;


    @ManyToOne
    @JoinColumn(name = "Sub_Sub_Category_id",nullable = false)
    private SubSubCategoryRegModel subSubCategory;


    @ManyToOne
    @JoinColumn(name = "Parlour_id",nullable = false)
    private ParlourRegModel parlour;

    @Column(name = "ServiceTime")
    private LocalTime ServiceTime;

    @Column(name = "Description")
    private String Description;

    @Column(name="Availability")
    private boolean Availability;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getActualPrice() {
        if (Price == null) {
            return BigDecimal.ZERO; // Set a default value if necessary
        }
        return Price;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.Price = actualPrice;
    }

}
