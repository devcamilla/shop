package com.dev.shop.models;

import com.dev.shop.dtos.CartItemInfo;

import javax.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    private String itemCode;

    private double quantity;

    protected CartItem() {}

    public CartItem(Cart cart, CartItemInfo cartItemInfo) {
        this.cart = cart;
        this.itemCode = cartItemInfo.getItemCode();
        this.quantity = cartItemInfo.getQuantity();
    }

    public String getItemCode() {
        return itemCode;
    }

    public double getQuantity() {
        return quantity;
    }
}
