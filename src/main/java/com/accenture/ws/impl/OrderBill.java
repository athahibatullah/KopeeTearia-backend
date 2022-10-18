package com.accenture.ws.impl;

import com.accenture.ws.entity.CafeClerk;
import com.accenture.ws.entity.Order;

import java.util.List;

abstract public class OrderBill {
    private List<Order> orderList;
    private CafeClerk clerk;

    public OrderBill() {
    }

    public OrderBill(CafeClerk clerk) {
        this.clerk = clerk;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public CafeClerk getClerk() {
        return clerk;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
    public abstract double getTotalBill(List<Order> order);
}
