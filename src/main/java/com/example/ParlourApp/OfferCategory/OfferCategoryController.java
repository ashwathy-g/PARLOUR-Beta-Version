package com.example.ParlourApp.OfferCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/offer-categories")
public class OfferCategoryController
{
    private final OfferCategoryService offerCategoryService;

    @Autowired
    public OfferCategoryController(OfferCategoryService offerCategoryService) {
        this.offerCategoryService = offerCategoryService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<OfferCategoryRegModel>> getAllOfferCategories() {
        return new ResponseEntity<>(offerCategoryService.getAllOfferCategories(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OfferCategoryRegModel> getOfferCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(offerCategoryService.getOfferCategoryById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_PARLOUR')")
    @PostMapping(path = "/offer")
    public ResponseEntity<?> createOfferCategory(@RequestPart("data") OfferCategoryRegModel offerCategory,
                                                 @RequestParam Long categoryId ,
                                                 @RequestPart("image") MultipartFile image) throws IOException {
        try {
            byte[] imageBytes = image.getBytes();
            OfferCategoryRegModel offerCategoryRegModel =offerCategoryService.createOffer(offerCategory,categoryId,imageBytes);
            return new ResponseEntity<>(offerCategoryRegModel,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.OK);
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<OfferCategoryRegModel> updateOfferCategory(@PathVariable Long id, @RequestBody OfferCategoryRegModel offerCategory) {
        return new ResponseEntity<>(offerCategoryService.updateOfferCategory(offerCategory), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteOfferCategory(@PathVariable Long id) {
        int count = offerCategoryService.deleteOfferCategory(id);
        if (count>0){
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (count<0) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
