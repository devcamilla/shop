package com.dev.shop.utilities;

import com.dev.shop.models.Amount;
import com.dev.shop.models.Cart;

public class Cashier {
    private Inventory inventory;

    public Cashier(Inventory inventory){
        this.inventory = inventory;
    }

    public Amount checkout(Cart cart) {
        double totalCost = cart.getCartItems().stream()
                .mapToDouble(cartItem -> {
                    InventoryItem inventoryItem = inventory.getItem(cartItem.getItemCode());
                    return inventoryItem.getUnitPrice().getValue() * cartItem.getQuantity();
                }).sum();

        return new Amount(totalCost);
    }
}
