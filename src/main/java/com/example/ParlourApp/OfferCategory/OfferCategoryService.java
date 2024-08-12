package com.example.ParlourApp.OfferCategory;

import com.example.ParlourApp.Offers.OfferRegModel;
import com.example.ParlourApp.Offers.OfferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OfferCategoryService
{
    private final OfferCategoryRepository offerCategoryRepository;

    @Autowired
    public OfferCategoryService(OfferCategoryRepository offerCategoryRepository) {
        this.offerCategoryRepository = offerCategoryRepository;
    }
    @Autowired
    private OfferRepository offerRepository;

    public List<OfferCategoryRegModel> getAllOfferCategories() {
        return offerCategoryRepository.findAll();
    }

    public OfferCategoryRegModel getOfferCategoryById(Long id) {
        return offerCategoryRepository.findById(id).orElse(null);
    }
    public OfferCategoryRegModel updateOfferCategory(OfferCategoryRegModel offerCategory) {
        return offerCategoryRepository.save(offerCategory);
    }

    public int deleteOfferCategory(Long id) {
        try {
            Optional<OfferCategoryRegModel> offerCategoryRegModel =offerCategoryRepository.findById(id);
            if (offerCategoryRegModel.isPresent()){
                offerCategoryRepository.deleteById(id);
                return 1;
            }
            return 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public OfferCategoryRegModel createOffer(OfferCategoryRegModel offerCategory, Long categoryId, byte[] imageBytes) {
        OfferCategoryRegModel offerCategoryRegModel = new OfferCategoryRegModel();
        offerCategoryRegModel.setName(offerCategory.getName());
        offerCategoryRegModel.setStartDate(offerCategory.getStartDate());
        offerCategoryRegModel.setEndDate(offerCategory.getEndDate());
        offerCategoryRegModel.setOfferPrice(offerCategory.getOfferPrice());
        offerCategoryRegModel.setCategoryId(categoryId);
        offerCategoryRegModel.setImage(imageBytes);


        Optional<OfferRegModel> offerRegModel = offerRepository.findById(categoryId);
        if (offerRegModel.isPresent()){
            OfferRegModel offerRegModel1 = offerRegModel.get();
            offerCategoryRegModel.setCategoryName(offerRegModel1.getName());
        }
        if (offerCategory.getDescription() == null) {
            offerCategoryRegModel.setDescription("No data");
        } else {
            offerCategoryRegModel.setDescription(offerCategory.getDescription());
        }
        if (offerCategory.getDescription() == null) {
            offerCategoryRegModel.setDescription("No data");
        } else {
            offerCategoryRegModel.setDescription(offerCategory.getDescription());
        }
        offerCategoryRepository.save(offerCategoryRegModel);
        return offerCategoryRegModel;
    }


}
