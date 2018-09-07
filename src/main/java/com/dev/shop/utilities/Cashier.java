package com.dev.shop.utilities;

import com.dev.shop.models.Amount;
import com.dev.shop.models.Cart;

public class Cashier {
    private Inventory inventory;

    private CouponRegistry couponRegistry;

    public Cashier(Inventory inventory){
        this.inventory = inventory;
    }

    public Cashier(Inventory inventory, CouponRegistry couponRegistry){
        this.inventory = inventory;
        this.couponRegistry = couponRegistry;
    }

    public Amount checkout(Cart cart) {
        double totalCost = cart.getCartItems().stream()
                .mapToDouble(cartItem -> {
                    Inventory.InventoryItem inventoryItem = inventory.getItem(cartItem.getItemCode());
                    return inventoryItem.getUnitPrice().getValue() * cartItem.getQuantity();
                }).sum();

        return new Amount(totalCost);
    }

    public Amount checkout(Cart cart, String couponCode) {
        CouponRegistry.Coupon coupon = couponRegistry.getCoupon(couponCode);

        double totalCost = cart.getCartItems().stream()
                .mapToDouble(cartItem -> {
                    Inventory.InventoryItem inventoryItem = inventory.getItem(cartItem.getItemCode());
                    double cost = inventoryItem.getUnitPrice().getValue() * cartItem.getQuantity();

                    if (!coupon.appliesTo(inventoryItem.getItemType())) return cost;

                    double discount = coupon.getPercent().applyTo(cost);
                    return cost - discount;
                }).sum();

        return new Amount(totalCost);
    }
}
