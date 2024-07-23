package com.example.ParlourApp.subcategory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class SubCategoryService
{
    @Autowired
    SubCategoryRepository subCategoryRepository;

    public List<SubCategoryRegModel> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    public SubCategoryRegModel getSubCategoryById(Long subCategoryId) {


        return subCategoryRepository.findById(subCategoryId).orElse(null);


    }
    public SubCategoryRegModel addSubCategory(SubCategoryRegModel subCategoryRegModel, MultipartFile image) {
        try {
            subCategoryRegModel.setImage(image.getBytes());
            return subCategoryRepository.save(subCategoryRegModel);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store image", e);
        }
    }
}

