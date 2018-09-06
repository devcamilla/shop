package com.dev.shop.dtos;

public class CartItemInfo {
    private String itemCode;

    private double quantity;

    public CartItemInfo(String itemCode, double quantity){
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
