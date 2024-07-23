package com.example.ParlourApp.Offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/offers")
public class OfferController
{
    @Autowired
    private OfferService offerService;

    @GetMapping(path = "/allDays")
    public ResponseEntity<List<OfferRegModel>> getAllOffers() {
        return new ResponseEntity<>(offerService.getAllOffers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferRegModel> getOfferById(@PathVariable Long id) {
        return new ResponseEntity<>(offerService.getOfferById(id), HttpStatus.OK);
    }

    @PostMapping("/offer")
    public ResponseEntity<OfferRegModel> createOffer(@RequestBody OfferRegModel offer) {
        return new ResponseEntity<>(offerService.createOffer(offer), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<OfferRegModel> updateOffer(@PathVariable Long id, @RequestBody OfferRegModel offer) {
        return new ResponseEntity<>(offerService.updateOffer(offer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
