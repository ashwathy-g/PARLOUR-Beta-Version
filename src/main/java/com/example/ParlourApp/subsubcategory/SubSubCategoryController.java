package com.example.ParlourApp.subsubcategory;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/SubSubCategory")
public class SubSubCategoryController
{
    @Autowired
    SubSubCategoryService subSubCategoryService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add_Sub")
    public ResponseEntity<SubSubCategoryRegModel> addSubSubCategory( @RequestParam("name") String name,
                                                                    @RequestParam("subCategoryId") Long subCategoryId,
                                                                    @RequestParam("image") MultipartFile image) {
        SubSubCategoryRegModel subSubCategoryRegModel =new SubSubCategoryRegModel();
        subSubCategoryRegModel.setName(name);
        try {
            subSubCategoryRegModel.setImage(image.getBytes());

        }catch (IOException e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        SubSubCategoryRegModel savedSubSubCategory=subSubCategoryService.addSubSubCategory(subSubCategoryRegModel,subCategoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubSubCategory);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubSubCategoryRegModel>> getAllSubSubCategories() {
        List<SubSubCategoryRegModel> subSubCategories = subSubCategoryService.getAllSubSubCategories();
        return ResponseEntity.ok(subSubCategories);
    }

}

