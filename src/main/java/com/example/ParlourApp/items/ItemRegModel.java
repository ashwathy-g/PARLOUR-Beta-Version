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
public class ItemRegModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "ItemName")
    private String ItemName;


    @Column(name = "ItemImage", columnDefinition = "bytea")
    private byte[] ItemImage;


    @Column(name = "Price")
    private BigDecimal Price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Category_id", nullable = false)
    private CategoryRegModel category;

    @ManyToOne
    @JoinColumn(name = "Sub_Category_id", nullable = false)
    private SubCategoryRegModel subCategory;


    @ManyToOne
    @JoinColumn(name = "Sub_Sub_Category_id", nullable = false)
    private SubSubCategoryRegModel subSubCategory;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Parlour_id", nullable = false)
    private ParlourRegModel parlour;

    public void setParlour(ParlourRegModel parlour) {
        this.parlour = parlour;
    }

    @Column(name = "ServiceTime")
    private LocalTime ServiceTime;

    @Column(name = "Description")
    private String Description;

    @Column(name = "Availability")
    private boolean Availability;


    public BigDecimal getActualPrice() {
        return Price;
    }
    public Long getCategoryId() {
        return category != null ? category.getId() : null;
    }

    public Long getSubCategoryId() {
        return subCategory != null ? subCategory.getId() : null;
    }

    public Long getSubSubCategoryId() {
        return subSubCategory != null ? subSubCategory.getId() : null;
    }
    public String getAvailability() {
        return Boolean.toString(Availability);
    }
    public void setAvailability(boolean availability) {
        Availability = availability;
    }
}
