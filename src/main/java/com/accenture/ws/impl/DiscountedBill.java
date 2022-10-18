package com.accenture.ws.impl;

import com.accenture.ws.entity.CafeClerk;
import com.accenture.ws.entity.Order;

import java.util.List;

public class DiscountedBill extends OrderBill{

    public DiscountedBill() {
    }

    public DiscountedBill(CafeClerk clerk) {
        super(clerk);
    }

    @Override
    public double getTotalBill(List<Order> ord) {
        double total = 0;
        for (int i = 0; i < ord.size(); i++ ) {
            if (ord.get(i).isDiscounted() == true) {
                double discountAmount = ord.get(i).getPrice() * 0.25;
                total += ord.get(i).getPrice() - discountAmount;
            } else {
                total+= ord.get(i).getPrice();
            }
        }
        return total;
    }
}
