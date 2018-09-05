package com.dev.shop.controllers;

import com.dev.shop.dtos.ItemInfo;
import com.dev.shop.models.*;
import com.dev.shop.repositories.ItemRepository;
import com.dev.shop.requestmodels.DiscountModel;
import com.dev.shop.requestmodels.ItemModel;
import com.dev.shop.requestmodels.WeightModel;
import com.dev.shop.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<ItemModel> getAll(){
        return itemRepository.findAll().stream()
                .map(item -> {
                    ItemModel itemModel = new ItemModel();
                    itemModel.id = item.getId();
                    itemModel.code = item.getCode();
                    itemModel.type = item.getType();
                    itemModel.unitPrice = item.getUnitPrice().getValue();

                    Weight unitWeight = item.getUnitWeight();
                    itemModel.unitWeight = new WeightModel(unitWeight.getValue(), unitWeight.getUnit().toString());

                    item.getDiscount().ifPresent(discount -> itemModel.discount = new DiscountModel(discount.getPercent().getValue(), discount.getValue().getValue()));

                    return itemModel;
                })
                .collect(Collectors.toList());
    }

    @PostMapping
    public long create(@RequestBody ItemModel itemModel){
        Optional<Discount> discount = itemModel.discount != null
                ? Optional.of(new Discount(new Percent(itemModel.discount.percent), new Amount(itemModel.discount.value)))
                : Optional.empty();

        Amount unitPrice = new Amount(itemModel.unitPrice);

        WeightModel unitWeightModel = itemModel.unitWeight;
        Weight unitWeight = new Weight(unitWeightModel.value, WeightUnit.valueOf(unitWeightModel.unit));

        ItemInfo itemInfo = new ItemInfo(itemModel.code, itemModel.type, unitPrice, unitWeight, discount);

        Item item = itemService.create(itemInfo);
        return item.getId();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody ItemModel itemModel){
        Optional<Discount> discount = itemModel.discount != null
                ? Optional.of(new Discount(new Percent(itemModel.discount.percent), new Amount(itemModel.discount.value)))
                : Optional.empty();

        Amount unitPrice = new Amount(itemModel.unitPrice);

        WeightModel unitWeightModel = itemModel.unitWeight;
        Weight unitWeight = new Weight(unitWeightModel.value, WeightUnit.valueOf(unitWeightModel.unit));

        ItemInfo itemInfo = new ItemInfo(itemModel.code, itemModel.type, unitPrice, unitWeight, discount);

        itemService.update(id, itemInfo);
    }

    @GetMapping("/{id}")
    public ItemModel getById(@PathVariable long id){
        return itemRepository.findById(id)
                .map(item -> {
                    ItemModel itemModel = new ItemModel();
                    itemModel.id = item.getId();
                    itemModel.code = item.getCode();
                    itemModel.type = item.getType();
                    itemModel.unitPrice = item.getUnitPrice().getValue();

                    Weight unitWeight = item.getUnitWeight();
                    itemModel.unitWeight = new WeightModel(unitWeight.getValue(), unitWeight.getUnit().toString());

                    item.getDiscount().ifPresent(discount -> itemModel.discount = new DiscountModel(discount.getPercent().getValue(), discount.getValue().getValue()));
                    return itemModel;
                })
                .orElseThrow(() -> new EntityNotFoundException());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){
        itemRepository.deleteById(id);
    }
}
