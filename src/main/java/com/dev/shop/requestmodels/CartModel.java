package com.dev.shop.requestmodels;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CartModel {
    public long id;

    @JsonProperty("items")
    public List<CartItemModel> cartItemModels;
}
