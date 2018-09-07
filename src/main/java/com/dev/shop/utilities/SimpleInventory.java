package com.dev.shop.utilities;

import com.dev.shop.models.Amount;
import com.dev.shop.models.Discount;
import com.dev.shop.models.ItemType;

import java.util.Optional;

public class SimpleInventory extends Inventory {
    public void addItem(String itemCode, ItemType itemType, Amount unitPrice, Discount discount){
        items.add(new InventoryItem(itemCode, itemType, unitPrice, java.util.Optional.ofNullable(discount)));
    }

    public void addItem(String itemCode, ItemType itemType, Amount unitPrice) {
        items.add(new InventoryItem(itemCode, itemType, unitPrice, Optional.empty()));
    }
}
