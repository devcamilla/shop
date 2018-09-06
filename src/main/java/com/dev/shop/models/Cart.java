package com.dev.shop.models;

import com.dev.shop.dtos.CartInfo;
import com.dev.shop.dtos.CartItemInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    protected Cart() {}

    public Cart(CartInfo cartInfo){
        if (cartInfo.getCartItemInfoList().size() < 0){
            throw new CartNoItemException();
        }

        for (CartItemInfo cartItemInfo: cartInfo.getCartItemInfoList()){
            CartItem cartItem = new CartItem(this, cartItemInfo);
            cartItems.add(cartItem);
        }
    }

    public long getId() {
        return id;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addCartItem(CartItemInfo cartItemInfo) {
        CartItem cartItem = new CartItem(this, cartItemInfo);
        cartItems.add(cartItem);
    }

    public void removeCartItem(String itemCode) {
        Optional<CartItem> cartItem = cartItems.stream()
                .filter(item -> item.getItemCode().equalsIgnoreCase(itemCode))
                .findAny();

        cartItem.ifPresent(item -> cartItems.remove(item));
    }

    public class CartNoItemException extends RuntimeException {
    }
}
