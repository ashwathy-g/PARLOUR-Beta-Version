package com.example.ParlourApp.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryRegModel,Long>
{
    Optional<CategoryRegModel> findById(Long categoryId);
}
