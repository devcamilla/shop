package com.dev.shop.utilities;

import com.dev.shop.models.Amount;
import com.dev.shop.models.Discount;
import com.dev.shop.models.ItemType;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Inventory {
    protected List<InventoryItem> items = new ArrayList<>();

    public InventoryItem getItem(String itemCode){
        return items.stream()
                .filter(item -> item.getCode().equalsIgnoreCase(itemCode))
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public class InventoryItem {
        private String code;

        private ItemType itemType;

        private Amount unitPrice;

        private Optional<Discount> discount;

        public InventoryItem(String code, ItemType itemType, Amount unitPrice, Optional<Discount> discount) {
            this.code = code;
            this.itemType = itemType;
            this.unitPrice = unitPrice;
            this.discount = discount;
        }

        public String getCode() {
            return code;
        }

        public ItemType getItemType() {
            return itemType;
        }

        public Amount getUnitPrice() {
            return unitPrice;
        }

        public Optional<Discount> getDiscount() {
            return discount;
        }

        public Amount getNetPrice(){
            return discount.isPresent()
                    ? AmountMath.substract(unitPrice, discount.get().applyTo(unitPrice))
                    : unitPrice;
        }
    }
}
