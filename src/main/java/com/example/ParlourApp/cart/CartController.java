package com.example.ParlourApp.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class CartController
{
    @Autowired
    CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<List<CartRegModel>> addItemToCart(@RequestBody List<CartRegModel> cartItems) {
        List<CartRegModel> addedItems = cartService.addItemToCart(cartItems);
        return ResponseEntity.ok(addedItems);
    }
}
