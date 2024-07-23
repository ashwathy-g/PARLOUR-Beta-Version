package com.example.ParlourApp.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartRegModel,Long>
{
    CartRegModel findByUserId(Long userId);


    List<CartRegModel> findAllByUniqueId(String uniqueId);
}
