package com.dev.shop.utilities;

import com.dev.shop.models.Item;
import com.dev.shop.repositories.ItemRepository;

import javax.persistence.EntityNotFoundException;

public class ShopInventory extends Inventory {
    private ItemRepository itemRepository;

    public ShopInventory(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Override
    public InventoryItem getItem(String itemCode){
        Item item = itemRepository.findByCode(itemCode);
        if (item == null) throw new EntityNotFoundException();
        return new InventoryItem(itemCode, item.getType(), item.getUnitPrice(), item.getDiscount());
    }
}
