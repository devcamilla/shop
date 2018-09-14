package com.dev.shop.repositories;

import com.dev.shop.models.Item;

public interface ItemRepository {
    Item findByCode(String code);
}
