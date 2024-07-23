package com.example.ParlourApp.items;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemRegModel,Long> {
    List<ItemRegModel> findByParlourId(Long parlourId);
}
