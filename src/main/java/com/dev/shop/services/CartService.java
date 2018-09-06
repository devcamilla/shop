package com.dev.shop.services;

import com.dev.shop.dtos.CartInfo;
import com.dev.shop.dtos.CartItemInfo;
import com.dev.shop.models.Cart;
import com.dev.shop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart create(CartInfo cartInfo) {
        Cart cart = new Cart(cartInfo);
        return cartRepository.save(cart);
    }

    public Cart addCartItem(long id, CartItemInfo cartItemInfo){
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        cart.addCartItem(cartItemInfo);
        return cartRepository.save(cart);
    }

    public Cart removeCartItem(long id, String itemCode){
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        cart.removeCartItem(itemCode);
        return cartRepository.save(cart);
    }
}
