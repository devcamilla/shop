package com.dev.shop.dtos;

import java.util.List;

public class CartInfo {
    private List<CartItemInfo> cartItemInfoList;

    public CartInfo(List<CartItemInfo> cartItemInfoList){
        this.cartItemInfoList = cartItemInfoList;
    }

    public List<CartItemInfo> getCartItemInfoList() {
        return cartItemInfoList;
    }
}
