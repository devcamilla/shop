package com.dev.shop.utilities;

import com.dev.shop.dtos.CartInfo;
import com.dev.shop.dtos.CartItemInfo;
import com.dev.shop.models.Amount;
import com.dev.shop.models.Cart;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class CashierTest {
    @Test
    public void checkoutCartShouldReturnTotalCost(){
        DefaultInventory inventory = new DefaultInventory();
        inventory.addItem(new InventoryItem("Apple", new Amount(10)));

        Cashier cashier = new Cashier(inventory);

        List<CartItemInfo> cartItemInfoList = new ArrayList<>();
        cartItemInfoList.add(new CartItemInfo("Apple", 10));

        CartInfo cartInfo = new CartInfo(cartItemInfoList);

        Cart cart = new Cart(cartInfo);

        Amount totalCost = cashier.checkout(cart);
        assertEquals(100d, totalCost.getValue());
    }
}
