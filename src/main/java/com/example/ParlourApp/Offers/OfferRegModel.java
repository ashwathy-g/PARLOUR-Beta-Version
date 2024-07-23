package com.example.ParlourApp.Offers;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Offers")
public class OfferRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public  OfferRegModel(String name,
                          String description){
        if (description==null){
            this.name = name;
            this.description = description != null? description:"No Details";
        }
    }

    public OfferRegModel() {
    }

}
