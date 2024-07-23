package com.example.ParlourApp.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class CategoryService
{
    @Autowired
    CategoryRepository categoryRepository;


    public List<CategoryRegModel> getAllCategories() {
        return categoryRepository.findAll();
    }
    public CategoryRegModel getCategoryById(Long categoryId) {

        return  categoryRepository.findById(categoryId).orElse(null);
    }

    public CategoryRegModel addCategory(CategoryRegModel categoryRegModel, MultipartFile image) {
        try {


            categoryRegModel.setImage(image.getBytes());

        }catch (Exception e){
            e.printStackTrace();
        }
        return categoryRepository.save(categoryRegModel);
    }

}
