package com.dev.shop.controllers;

import com.dev.shop.assemblers.ItemAssembler;
import com.dev.shop.dtos.ItemInfo;
import com.dev.shop.models.Item;
import com.dev.shop.repositories.JpaItemRepository;
import com.dev.shop.requestmodels.ItemModel;
import com.dev.shop.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private JpaItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity getAll(){
        List<ItemModel> itemModels = itemRepository.findAll().stream()
                .map(item -> ItemAssembler.toItemModel(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(itemModels);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody ItemModel itemModel){
        ItemInfo itemInfo = ItemAssembler.toItemInfo(itemModel);
        Item item = itemService.create(itemInfo);
        return ResponseEntity.ok(item.getId());
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody ItemModel itemModel){
        ItemInfo itemInfo = ItemAssembler.toItemInfo(itemModel);
        itemService.update(id, itemInfo);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable long id){
        Optional<ItemModel> itemModel = itemRepository.findById(id)
                .map(item -> ItemAssembler.toItemModel(item));

        if (!itemModel.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemModel);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){
        itemRepository.deleteById(id);
    }
}
