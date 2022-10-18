package com.accenture.ws;

import com.accenture.ws.entity.Order;
import com.accenture.ws.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderBillingWsApplicationTests {

	@Autowired
	OrderRepository orderRepository;
	@Test
	@org.junit.jupiter.api.Order(1)
	void addOrderTest(){
		Order order = new Order();

		order.setOrderName("Cafe Latte");
		order.setPrice(3.5);
		order.setDiscounted(true);

		orderRepository.save(order);
		Assertions.assertThat(order.getId()).isGreaterThan(0);
	}
	@Test
	@org.junit.jupiter.api.Order(2)
	void getOrderListTest() {
		List<Order> orderList = orderRepository.findAll();
		Assertions.assertThat(orderList.size()).isGreaterThan(0);
	}
	@Test
	@org.junit.jupiter.api.Order(3)
	void updateOrder(){

		Order order = new Order();
		List<Order> orderList = orderRepository.findAll();

		for(int i=0;i<orderList.size();i++){
			if(orderList.get(i).getOrderName().equals("Cafe Latte")){
				order = orderList.get(i);
				order.setOrderName("Cafe Mocha");
				order.setDiscounted(false);
			}
		}
		orderRepository.save(order);
		Assertions.assertThat(order.getOrderName()).isEqualTo("Cafe Mocha");
	}
	@Test
	@org.junit.jupiter.api.Order(4)
	void forgetOrderListTest(){
		Order order = new Order();
		List<Order> orderList = orderRepository.findAll();

		for(int i=0;i<orderList.size();i++){
			if(orderList.get(i).getOrderName().equals("Cafe Mocha")){
				order = orderList.get(i);
				break;
			}
		}
		Long id = order.getId();
		orderRepository.deleteById(id);
		Order orderNull = null;
		Optional<Order> orderDeleted = orderRepository.findById(id);
		if (orderDeleted.isPresent()) {
			orderNull = orderDeleted.get();
		}
		Assertions.assertThat(orderNull).isNull();
	}
}
