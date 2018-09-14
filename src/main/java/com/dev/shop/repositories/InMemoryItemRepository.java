package com.dev.shop.repositories;

import com.dev.shop.dtos.ItemInfo;
import com.dev.shop.models.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryItemRepository implements ItemRepository {
    private List<Item> items = new ArrayList<>();

    public void addItem(String itemCode, ItemType itemType, Amount unitPrice, Weight unitWeight, Discount discount){
        ItemInfo itemInfo = new ItemInfo(itemCode, itemType, unitPrice, unitWeight, Optional.ofNullable(discount));
        items.add(new Item(itemInfo));
    }

    public void addItem(String itemCode, ItemType itemType, Amount unitPrice, Weight unitWeight){
        ItemInfo itemInfo = new ItemInfo(itemCode, itemType, unitPrice, unitWeight, Optional.empty());
        items.add(new Item(itemInfo));
    }

    @Override
    public Item findByCode(String code) {
        return items.stream()
                .filter(item -> item.getCode().equalsIgnoreCase(code))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException());
    }
}
