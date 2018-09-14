package com.dev.shop.repositories;

import com.dev.shop.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaItemRepository extends JpaRepository<Item, Long>, ItemRepository {
}
