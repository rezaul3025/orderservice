package org.order.persistence.service;

import org.order.domain.Order;
import org.order.persistence.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceHandler implements OrderService {
	
	@Autowired
	private OrderRepository orderRepo;

	@Override
	public Order getOrderById(Long id) {
		return orderRepo.findById(id);
	}

}
