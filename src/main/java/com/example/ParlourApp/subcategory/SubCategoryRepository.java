package com.example.ParlourApp.subcategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoryRepository  extends JpaRepository<SubCategoryRegModel,Long>
{
    Optional<SubCategoryRegModel> findById(Long subCategoryId);
}
