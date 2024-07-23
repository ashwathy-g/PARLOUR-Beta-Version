package com.example.ParlourApp.items;

import com.example.ParlourApp.category.CategoryRegModel;
import com.example.ParlourApp.category.CategoryService;
import com.example.ParlourApp.jwt.JwtUtil;
import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import com.example.ParlourApp.parlour.ParlourService;
import com.example.ParlourApp.subcategory.SubCategoryRegModel;
import com.example.ParlourApp.subcategory.SubCategoryService;
import com.example.ParlourApp.subsubcategory.SubSubCategoryRegModel;
import com.example.ParlourApp.subsubcategory.SubSubCategoryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/Items")
public class ItemController
{
    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ParlourRepository parlourRepository;

    @Autowired
    CategoryService categoryService;
    @Autowired
    SubCategoryService subCategoryService;

    @Autowired
    SubSubCategoryService subSubCategoryService;
    @Autowired
    ParlourService parlourService;
    @Autowired
    JwtUtil jwtUtil;
    @PreAuthorize("hasAuthority('ROLE_PARLOUR')")
    @PostMapping("/AddItems")
    public ResponseEntity<ItemRegModel> addItem(@RequestParam("itemName") String itemName,
                                                @RequestParam("itemImage") MultipartFile itemImage,
                                                @RequestParam("price") BigDecimal price,
                                                @RequestParam("categoryId") Long categoryId,
                                                @RequestParam("subCategoryId") Long subCategoryId,
                                                @RequestParam("subSubCategoryId")Long subSubCategoryId,
                                                @RequestParam("parlourId") Long parlourId,
                                                @RequestParam("serviceTime") LocalTime serviceTime,
                                                @RequestParam("description") String description,
                                                @RequestParam("availability") boolean availability)  {



        try {
            if (itemImage!=null&&!itemImage.isEmpty())
            {
               ItemRegModel itemRegModel=new ItemRegModel();
                itemRegModel.setItemName(itemName);
                itemRegModel.setItemImage(itemImage.getBytes());
                itemRegModel.setPrice(price);
                CategoryRegModel category = categoryService.getCategoryById(categoryId);
                SubCategoryRegModel subCategory = subCategoryService.getSubCategoryById(subCategoryId);
                Optional<SubSubCategoryRegModel> subSubCategoryOpt=subSubCategoryService.getSubSubCategoryById(subSubCategoryId);
                itemRegModel.setCategory(category);
                itemRegModel.setSubCategory(subCategory);
                if (subSubCategoryOpt.isPresent()) {
                    SubSubCategoryRegModel subSubCategory = subSubCategoryOpt.get();
                    itemRegModel.setSubSubCategory(subSubCategory);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
                ParlourRegModel parlour=parlourService.getParlourById(parlourId);
                itemRegModel.setParlour(parlour);
                itemRegModel.setServiceTime(serviceTime);
                itemRegModel.setDescription(description);
                itemRegModel.setAvailability(availability);
                ItemRegModel addedItem = itemService.addItem(itemRegModel);
                return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/getAllItems")
    public ResponseEntity<List<ItemRegModel>> getAllItems()
    {
        List<ItemRegModel>items=itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemRegModel>getItemById(@PathVariable Long itemId)
    {
        Optional<ItemRegModel> item=itemService.getItemById(itemId);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    @PutMapping("/{itemId}")
    public ResponseEntity<ItemRegModel> updateItem(
            @PathVariable Long itemId,
            @RequestParam("itemName") String itemName,
            @RequestParam(value = "itemImage", required = false) MultipartFile itemImage,
            @RequestParam("price") double price,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("subCategoryId") Long subCategoryId,
            @RequestParam("subSubCategoryId")Long subSubCategoryId,
            @RequestParam("parlourId") Long parlourId,
            @RequestParam(value = "serviceTime", required = false) String serviceTime,
            @RequestParam("description") String description) {

        Optional<ItemRegModel> updatedItem = itemService.updateItem(itemId, itemName, itemImage, price, categoryId, subCategoryId,subSubCategoryId,parlourId, serviceTime, description);
        return updatedItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }

}
