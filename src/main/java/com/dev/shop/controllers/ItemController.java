package com.dev.shop.controllers;

import com.dev.shop.assemblers.ItemAssembler;
import com.dev.shop.dtos.ItemInfo;
import com.dev.shop.models.Item;
import com.dev.shop.repositories.ItemRepository;
import com.dev.shop.requestmodels.ItemModel;
import com.dev.shop.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<ItemModel> getAll(){
        return itemRepository.findAll().stream()
                .map(item -> ItemAssembler.toItemModel(item))
                .collect(Collectors.toList());
    }

    @PostMapping
    public long create(@RequestBody ItemModel itemModel){
        ItemInfo itemInfo = ItemAssembler.toItemInfo(itemModel);
        Item item = itemService.create(itemInfo);
        return item.getId();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody ItemModel itemModel){
        ItemInfo itemInfo = ItemAssembler.toItemInfo(itemModel);
        itemService.update(id, itemInfo);
    }

    @GetMapping("/{id}")
    public ItemModel getById(@PathVariable long id){
        return itemRepository.findById(id)
                .map(item -> ItemAssembler.toItemModel(item))
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){
        itemRepository.deleteById(id);
    }
}
