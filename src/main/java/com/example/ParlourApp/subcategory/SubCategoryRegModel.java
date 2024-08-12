package com.example.ParlourApp.subcategory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "SubCategory")
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "image",columnDefinition = "bytea")
    private byte[] image;

    @Column(name = "categoryId")
    private Integer categoryId;


}
