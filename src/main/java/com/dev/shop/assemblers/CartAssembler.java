package com.dev.shop.assemblers;

import com.dev.shop.dtos.CartInfo;
import com.dev.shop.dtos.CartItemInfo;
import com.dev.shop.models.Cart;
import com.dev.shop.requestmodels.CartItemModel;
import com.dev.shop.requestmodels.CartModel;

import java.util.List;
import java.util.stream.Collectors;

public class CartAssembler {
    public static CartModel toCartModel(Cart cart){
        CartModel cartModel = new CartModel();
        cartModel.id = cart.getId();

        cartModel.cartItemModels = cart.getCartItems().stream()
                .map(cartItem -> {
                    CartItemModel cartItemModel = new CartItemModel();

                    cartItemModel.itemCode = cartItem.getItemCode();
                    cartItemModel.quantity = cartItem.getQuantity();
                    return cartItemModel;
                })
                .collect(Collectors.toList());

        return cartModel;
    }

    public static CartInfo toCartInfo(CartModel cartModel){
        List<CartItemInfo> cartItemInfoList = cartModel.cartItemModels.stream()
                .map(cartItemModel -> new CartItemInfo(cartItemModel.itemCode, cartItemModel.quantity))
                .collect(Collectors.toList());

        return new CartInfo(cartItemInfoList);
    }
}
