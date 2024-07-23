package com.example.ParlourApp.subcategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/SubCategory")
public class SubCategoryController
{
    @Autowired
    SubCategoryService  subCategoryService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add_Sub")

    public ResponseEntity<SubCategoryRegModel> addSubCategory(@RequestParam("name") String name,
                                                              @RequestParam("categoryId") Integer categoryId,
                                                              @RequestParam("image") MultipartFile image){

         SubCategoryRegModel subCategoryRegModel=new SubCategoryRegModel();
         subCategoryRegModel.setName(name);
         subCategoryRegModel.setCategoryId(categoryId);
        SubCategoryRegModel subCategoryRegModel1 = subCategoryService.addSubCategory(subCategoryRegModel,image);
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategoryRegModel1);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubCategoryRegModel>> getAllSubCategories() {
        List<SubCategoryRegModel> subCategories = subCategoryService.getAllSubCategories();
        return ResponseEntity.ok(subCategories);
    }



}
