package com.example.ParlourApp.Offers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OfferService
{
    @Autowired
    private  OfferRepository offerRepository;
    public List<OfferRegModel> getAllOffers() {
        return offerRepository.findAll();
    }

    public OfferRegModel getOfferById(Long id) {
        return offerRepository.findById(id).orElse(null);
    }

    public OfferRegModel createOffer(OfferRegModel offer) {
        return offerRepository.save(offer);
    }

    public OfferRegModel updateOffer(OfferRegModel offer) {
        return offerRepository.save(offer);
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

}
