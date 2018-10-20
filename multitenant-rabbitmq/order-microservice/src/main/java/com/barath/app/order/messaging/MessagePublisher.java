package com.barath.app.order.messaging;

import com.barath.app.order.entity.OrderItem;

/**
 * A simple interface for order publisher.
 *
 *<p>
 *An implementation class can implement MessagePublisher 
 *and describe where to post the oder 
 *</p>
 */
public interface MessagePublisher {

	void placeOrder(OrderItem orderItems);

	void cancelOrder(OrderItem orderDetails);
	
}
