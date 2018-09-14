package com.dev.shop.services;

import com.dev.shop.dtos.ItemInfo;
import com.dev.shop.models.Item;
import com.dev.shop.repositories.JpaItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ItemService {
    @Autowired
    private JpaItemRepository itemRepository;

    public Item create(ItemInfo itemInfo) {
        Item item = new Item(itemInfo);
        return itemRepository.save(item);
    }

    public void update(long id, ItemInfo itemInfo) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        item.update(itemInfo);
        itemRepository.save(item);
    }
}
