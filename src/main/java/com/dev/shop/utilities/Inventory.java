package com.dev.shop.utilities;

import com.dev.shop.models.Amount;
import com.dev.shop.models.ItemType;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

        protected InventoryItem(String code, Amount unitPrice){
            this.code = code;
            this.unitPrice = unitPrice;
        }

        public InventoryItem(String code, ItemType itemType, Amount unitPrice) {
            this.code = code;
            this.itemType = itemType;
            this.unitPrice = unitPrice;
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
    }
}
