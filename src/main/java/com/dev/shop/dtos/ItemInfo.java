package com.dev.shop.dtos;

import com.dev.shop.models.Amount;
import com.dev.shop.models.Discount;
import com.dev.shop.models.ItemType;
import com.dev.shop.models.Weight;

import java.util.Optional;

public class ItemInfo {
    private String code;

    private ItemType type;

    private Amount unitPrice;

    private Weight unitWeight;

    private Optional<Discount> discount;

    public ItemInfo(String code, ItemType type, Amount unitPrice, Weight unitWeight, Optional<Discount> discount){
        this.code = code;
        this.type = type;
        this.unitPrice = unitPrice;
        this.unitWeight = unitWeight;
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public ItemType getType() {
        return type;
    }

    public Amount getUnitPrice() {
        return unitPrice;
    }

    public Weight getUnitWeight() {
        return unitWeight;
    }

    public Optional<Discount> getDiscount() {
        return discount;
    }
}
