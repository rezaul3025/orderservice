package org.order.controller;

import org.order.domain.Order;
import org.order.persistence.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="getdetails/{id}")
	public Order getOrderDetails(@PathVariable("id") Long id){
		return orderService.getOrderById(id);
	}
}
