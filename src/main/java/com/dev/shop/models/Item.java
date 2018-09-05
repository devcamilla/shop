package com.dev.shop.models;

import com.dev.shop.dtos.ItemInfo;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code;

    private String type;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "unitprice_value"))
    private Amount unitPrice;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "unitweight_value")),
        @AttributeOverride(name = "unit", column = @Column(name = "unitweight_unit"))})
    private Weight unitWeight;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "percent", column = @Column(name = "discount_percent")),
            @AttributeOverride(name = "value", column = @Column(name = "discount_value"))})
    private Discount discount;

    protected Item() {}

    public Item(ItemInfo itemInfo){
        update(itemInfo);
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public Amount getUnitPrice() {
        return unitPrice;
    }

    public Weight getUnitWeight() {
        return unitWeight;
    }

    public Optional<Discount> getDiscount() {
        return Optional.ofNullable(discount);
    }

    public void update(ItemInfo itemInfo) {
        code = itemInfo.getCode();
        type = itemInfo.getType();
        unitPrice = itemInfo.getUnitPrice();
        unitWeight = itemInfo.getUnitWeight();

        itemInfo.getDiscount().ifPresent(discount -> this.discount = discount);
    }
}
