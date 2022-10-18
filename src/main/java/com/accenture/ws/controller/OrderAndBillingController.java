package com.accenture.ws.controller;

import com.accenture.ws.entity.CafeClerk;
import com.accenture.ws.entity.Order;
import com.accenture.ws.impl.DiscountedBill;
import com.accenture.ws.impl.RegularBill;
import com.accenture.ws.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("")
public class OrderAndBillingController {
    private CafeClerk clerk;
    @Autowired
    OrderRepository orderRepo;

    public OrderAndBillingController(){
    }
    public OrderAndBillingController(CafeClerk clerk){
        this.clerk = clerk;
    }
    @GetMapping("/get")
    public List<Order> getOrderList(){
        return orderRepo.findAll();
    }
    @GetMapping("/regular")
    public ResponseEntity<?> getTotalRegularBill(){
        List<Order> orderItems = orderRepo.findAll();
        RegularBill r = new RegularBill();
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        map.put("regular", r.getTotalBill(orderItems));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @GetMapping("/discount")
    public ResponseEntity<?> getTotalDiscountBill(){
        List<Order> orderItems = orderRepo.findAll();
        DiscountedBill d = new DiscountedBill();
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        map.put("discount", d.getTotalBill(orderItems));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @PostMapping("/add")
    public void addOrder(@RequestBody Order order){
        orderRepo.save(order);
    }
    @PutMapping("/update")
    public void updateOrder(@RequestBody Order order){
        Long id = order.getId();
        Order newOrder = new Order();
        try{
            newOrder = orderRepo.findById(id).get();

            newOrder.setOrderName(order.getOrderName());
            newOrder.setPrice(order.getPrice());
            newOrder.setDiscounted(order.isDiscounted());
        }
        catch (NoSuchElementException e){
            throw new RuntimeException("Order with name = " + order.getOrderName() + " does not exist in the database!");
        }
        orderRepo.save(newOrder);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Long id){
        orderRepo.deleteById(id);
    }
}
