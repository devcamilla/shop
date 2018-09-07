package com.dev.shop.utilities;

import com.dev.shop.models.Amount;
import com.dev.shop.models.Cart;
import com.dev.shop.models.ItemType;
import com.dev.shop.models.Percent;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CashierTest {
    @Test
    public void checkout_ShouldReturnTotalCost(){
        SimpleInventory inventory = new SimpleInventory();
        inventory.addItem("Apple", new Amount(10));

        Cart cart = new Cart();
        cart.addItem("Apple", 10);

        Cashier cashier = new Cashier(inventory);
        Amount totalCost = cashier.checkout(cart);

        assertEquals(100d, totalCost.getValue());
    }

    @Test
    public void checkout_ShouldReturnTotalCost_UsingStoreWideCoupon(){
        SimpleInventory inventory = new SimpleInventory();
        inventory.addItem("Apple", new Amount(10));

        Cart cart = new Cart();
        cart.addItem("Apple", 10);

        SimpleCouponRegistry couponRegistry = new SimpleCouponRegistry();
        couponRegistry.addCoupon("ALL10%OFF", new Percent(10));

        Cashier cashier = new Cashier(inventory, couponRegistry);
        Amount totalCost = cashier.checkout(cart, "ALL10%OFF");

        assertEquals(90d, totalCost.getValue());
    }

    @Test
    public void checkout_ShouldReturnTotalCost_UsingItemTypeCoupon(){
        SimpleInventory inventory = new SimpleInventory();
        inventory.addItem("Apple", new ItemType("FRUIT"), new Amount(10));

        Cart cart = new Cart();
        cart.addItem("Apple", 10);

        SimpleCouponRegistry couponRegistry = new SimpleCouponRegistry();
        couponRegistry.addCoupon("FRUIT10%OFF", new Percent(10), new ItemType("FRUIT"));

        Cashier cashier = new Cashier(inventory, couponRegistry);
        Amount totalCost = cashier.checkout(cart, "FRUIT10%OFF");

        assertEquals(90d, totalCost.getValue());
    }

    @Test
    public void checkout_ShouldReturnTotalCost_UsingItemTypeCoupon_NotApplicableItemType(){
        SimpleInventory inventory = new SimpleInventory();
        inventory.addItem("Squash", new ItemType("VEG"), new Amount(10));

        Cart cart = new Cart();
        cart.addItem("Squash", 10);

        SimpleCouponRegistry couponRegistry = new SimpleCouponRegistry();
        couponRegistry.addCoupon("FRUIT10%OFF", new Percent(10), new ItemType("FRUIT"));

        Cashier cashier = new Cashier(inventory, couponRegistry);
        Amount totalCost = cashier.checkout(cart, "FRUIT10%OFF");

        assertEquals(100d, totalCost.getValue());
    }
}
