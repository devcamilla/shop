package com.dev.shop.utilities;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class Inventory {
    protected List<InventoryItem> items = new ArrayList<>();

    public void addItem(InventoryItem inventoryItem){
        items.add(inventoryItem);
    }

    public void removeItem(String itemCode){
        InventoryItem item = getItem(itemCode);
        items.remove(item);
    }

    public InventoryItem getItem(String itemCode){
        return items.stream()
                .filter(item -> item.getCode().equalsIgnoreCase(itemCode))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException());
    }
}
