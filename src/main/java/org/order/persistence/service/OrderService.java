package org.order.persistence.service;

import org.order.domain.Order;

public interface OrderService {
	Order getOrderById(Long id);
}
