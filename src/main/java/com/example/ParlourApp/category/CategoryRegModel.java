package com.example.ParlourApp.category;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Category")
public class CategoryRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image",columnDefinition = "bytea")
    private byte[] image;

}
