package com.barath.app.inventory.messaging;

import com.barath.app.inventory.dto.Order;

/**
 *Interface MessagePublisher to confirm the order. 
 *
 *
 */
public interface MessagePublisher {
	
	void confirmOrder(Order order);
	
}
