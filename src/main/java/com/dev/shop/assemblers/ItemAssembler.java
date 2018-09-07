package com.dev.shop.assemblers;

import com.dev.shop.dtos.ItemInfo;
import com.dev.shop.models.*;
import com.dev.shop.requestmodels.DiscountModel;
import com.dev.shop.requestmodels.ItemModel;
import com.dev.shop.requestmodels.WeightModel;

import java.util.Optional;

public class ItemAssembler {
    public static ItemModel toItemModel(Item item){
        ItemModel itemModel = new ItemModel();

        itemModel.id = item.getId();
        itemModel.code = item.getCode();
        itemModel.type = item.getType().getValue();
        itemModel.unitPrice = item.getUnitPrice().getValue();

        Weight unitWeight = item.getUnitWeight();
        itemModel.unitWeight = new WeightModel(unitWeight.getValue(), unitWeight.getUnit().toString());

        item.getDiscount().ifPresent(discount -> itemModel.discount = new DiscountModel(discount.getPercent().getValue(), discount.getValue().getValue()));

        return itemModel;
    }

    public static ItemInfo toItemInfo(ItemModel itemModel){
        Optional<Discount> discount = itemModel.discount != null
                ? Optional.of(new Discount(new Percent(itemModel.discount.percent), new Amount(itemModel.discount.value)))
                : Optional.empty();

        ItemType type = new ItemType(itemModel.type);
        Amount unitPrice = new Amount(itemModel.unitPrice);

        WeightModel unitWeightModel = itemModel.unitWeight;
        Weight unitWeight = new Weight(unitWeightModel.value, WeightUnit.valueOf(unitWeightModel.unit));

        return new ItemInfo(itemModel.code, type, unitPrice, unitWeight, discount);
    }
}
