package com.dev.shop.models;

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

    public Cart() {}

    public long getId() {
        return id;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addItem(String itemCode, double quantity) {
        CartItem cartItem = new CartItem(this, itemCode, quantity);
        cartItems.add(cartItem);
    }

    public void removeItem(String itemCode) {
        Optional<CartItem> cartItem = cartItems.stream()
                .filter(item -> item.getItemCode().equalsIgnoreCase(itemCode))
                .findAny();

        cartItem.ifPresent(item -> cartItems.remove(item));
    }
}
