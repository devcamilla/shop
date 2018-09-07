package com.dev.shop.services;

import com.dev.shop.models.Cart;
import com.dev.shop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart create() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    public Cart addItem(long id, String itemCode, double quantity){
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        cart.addItem(itemCode, quantity);
        return cartRepository.save(cart);
    }

    public Cart removeItem(long id, String itemCode){
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        cart.removeItem(itemCode);
        return cartRepository.save(cart);
    }
}
