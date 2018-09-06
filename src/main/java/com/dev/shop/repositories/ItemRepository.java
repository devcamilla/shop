package com.dev.shop.repositories;

import com.dev.shop.models.Item;
import com.dev.shop.utilities.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByCode(String code);
}
