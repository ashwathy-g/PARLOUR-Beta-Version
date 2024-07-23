package com.example.ParlourApp.subsubcategory;

import com.example.ParlourApp.subcategory.SubCategoryRegModel;
import com.example.ParlourApp.subcategory.SubCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SubSubCategoryService
{
    @Autowired
    SubSubCategoryRepository subSubCategoryRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;

    public SubSubCategoryRegModel addSubSubCategory(SubSubCategoryRegModel subSubCategoryRegModel ,Long subCategoryId){
        SubCategoryRegModel subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid subCategoryId: " + subCategoryId));

        subSubCategoryRegModel.setSubCategory(subCategory);


         return subSubCategoryRepository.save(subSubCategoryRegModel);
    }
    public List<SubSubCategoryRegModel> getAllSubSubCategories() {
        return subSubCategoryRepository.findAll();
    }
    public Optional<SubSubCategoryRegModel> getSubSubCategoryById(Long id) {
        return subSubCategoryRepository.findById(id);
    }

}




