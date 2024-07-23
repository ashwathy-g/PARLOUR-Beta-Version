package com.example.ParlourApp.subsubcategory;

import com.example.ParlourApp.subcategory.SubCategoryRegModel;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@Entity
@Table(name = "SubSUbCategory")
public class SubSubCategoryRegModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategoryRegModel subCategory;


    @Column(name = "name")
    private  String name;

    @Column(name = "image",columnDefinition = "bytea")
    private byte[] image;




    public byte[] getImage() {
        return image;
    }


    public void setImage(byte[]image)
    {
        this.image= image;

    }

}
