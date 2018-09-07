package com.dev.shop.models;

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

    protected CartItem(Cart cart, String itemCode, double quantity) {
        this.cart = cart;
        this.itemCode = itemCode;
        this.quantity = quantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public double getQuantity() {
        return quantity;
    }
}
