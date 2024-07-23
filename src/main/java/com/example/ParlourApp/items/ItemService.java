package com.example.ParlourApp.items;

import com.example.ParlourApp.category.CategoryRegModel;
import com.example.ParlourApp.category.CategoryRepository;
import com.example.ParlourApp.category.CategoryService;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import com.example.ParlourApp.subcategory.SubCategoryRegModel;
import com.example.ParlourApp.subcategory.SubCategoryRepository;
import com.example.ParlourApp.subcategory.SubCategoryService;
import com.example.ParlourApp.subsubcategory.SubSubCategoryRegModel;
import com.example.ParlourApp.subsubcategory.SubSubCategoryRepository;
import com.example.ParlourApp.subsubcategory.SubSubCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Validated
public class ItemService
{
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    SubSubCategoryRepository subSubCategoryRepository;

    @Autowired
    SubSubCategoryService subSubCategoryService;

    @Autowired
    ParlourRepository parlourRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    SubCategoryService subCategoryService;

    public ItemRegModel addItem(ItemRegModel item) {
        return itemRepository.save(item);
    }

    public CategoryRegModel findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public SubCategoryRegModel findSubCategoryById(Long id) {
        return subCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Sub Category not found"));
    }

    public SubSubCategoryRegModel findSubSubCategoryById(Long id) {
        return subSubCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Sub Sub Category not found"));
    }

    public ParlourRegModel findParlourById(Long id) {
        return parlourRepository.findById(id).orElseThrow(() -> new RuntimeException("Parlour not found"));
    }
    public List<ItemRegModel> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<ItemRegModel> getItemById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public Optional<ItemRegModel> updateItem(Long itemId, String itemName, MultipartFile itemImage, double price, Long categoryId, Long subCategoryId, Long subSubCategoryId,Long parlourId, String serviceTime, String description) {
        Optional<ItemRegModel> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            ItemRegModel existingItem = optionalItem.get();

            existingItem.setItemName(itemName);
            if (itemImage != null && !itemImage.isEmpty()) {
                try {
                    existingItem.setItemImage(itemImage.getBytes());
                }catch (IOException e){
                    throw new RuntimeException("Failed to convert MultipartFile to byte[]", e);
                }
            }

            existingItem.setPrice(BigDecimal.valueOf(price));
            CategoryRegModel category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
            SubCategoryRegModel subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new RuntimeException("SubCategory not found"));
            SubSubCategoryRegModel subSubCategory=subSubCategoryRepository.findById(subSubCategoryId).orElseThrow(()->new RuntimeException("SubSubCategory not found"));
            ParlourRegModel parlour = parlourRepository.findById(parlourId).orElseThrow(() -> new RuntimeException("Parlour not found"));

            existingItem.setCategory(category);
            existingItem.setSubCategory(subCategory);
            existingItem.setSubSubCategory(subSubCategory);
            existingItem.setParlour(parlour);

            if (serviceTime != null) {
                existingItem.setServiceTime(LocalTime.parse(serviceTime));
            }

            existingItem.setDescription(description);

            itemRepository.save(existingItem);
            return Optional.of(existingItem);
        } else {
            return Optional.empty();
        }
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
    private void validateItem(ItemRegModel itemRegModel) {
        if (itemRegModel.getCategory() == null) {
            throw new NullPointerException("Category cannot be null.");
        }
    }
}




