package com.dev.shop.utilities;

import com.dev.shop.models.Amount;
import com.dev.shop.models.Cart;

import java.util.Optional;

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
        return checkout(cart, Optional.empty());
    }

    public Amount checkout(Cart cart, String couponCode) {
        Optional<CouponRegistry.Coupon> coupon = couponRegistry.getCoupon(couponCode);
        return checkout(cart, coupon);
    }

    private Amount checkout(Cart cart, Optional<CouponRegistry.Coupon> coupon) {
        double totalCost = cart.getCartItems().stream()
                .mapToDouble(cartItem -> {
                    Inventory.InventoryItem inventoryItem = inventory.getItem(cartItem.getItemCode());
                    double cost = inventoryItem.getNetPrice().getValue() * cartItem.getQuantity();

                    if (!coupon.isPresent()) return cost;

                    if (!coupon.get().appliesTo(inventoryItem.getItemType())) return cost;

                    double discount = coupon.get().getPercent().applyTo(cost);
                    return cost - discount;
                }).sum();

        return new Amount(totalCost);
    }
}
