package com.dev.shop.utilities;

import com.dev.shop.models.Amount;
import com.dev.shop.models.ItemType;

public class SimpleInventory extends Inventory {
    public void addItem(String itemCode, Amount unitPrice){
        items.add(new InventoryItem(itemCode, unitPrice));
    }

    public void addItem(String itemCode, ItemType itemType, Amount unitPrice) {
        items.add(new InventoryItem(itemCode, itemType, unitPrice));
    }
}
