package com.barath.app.order.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.barath.app.order.entity.OrderItem;
import com.barath.app.order.exception.OrderItemsException;
import com.barath.app.order.messaging.MessagePublisher;
import com.barath.app.order.service.OrderService;

/**
 * Controller class providing REST endpoints for order microservice.
 * <p>
 * RestController class for the Order microservice which maps the request api's
 * to the corresponding methods
 * </p>
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	

	/**
	 * Index endpoint for order microservice.
	 * 
	 * @return ResponseBody Welcome Message
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "Welcome to Order Microservice";

	}

	/**
	 * Fetching the OrderItems entity by it's orderId.
	 * 
	 * @param orderId
	 *            Long value that contains the id
	 * @return ResponseBody OrderItems
	 */
	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	public OrderItem byNumber(@PathVariable("orderId") Long orderId) {
		System.out.println("Order ID " + orderId);

		OrderItem productRes = orderService.getOrderItemById(orderId);
		if (productRes == null) {
			throw new OrderItemsException();
		} else {
			return productRes;
		}
	}

	/**
	 * Saving the incoming orderItem object in Database.
	 * 
	 * @param orderSample
	 *            Incoming OrderItem Entity
	 * @return ResponseBody OrderItems
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public OrderItem placeOrder(@RequestBody OrderItem orderSample) {
		LOGGER.debug("Order: ProductName="+orderSample.getProductName()+", locationName="+orderSample.getLocationName());
		return orderService.saveOrderItems(orderSample);

	}

	

	@RequestMapping(value = "/order/getByProductName/{productName}", method = RequestMethod.GET)
	public Collection<OrderItem> byProductName(@PathVariable("productName") String productName) {

		return orderService.getOrderByProductName(productName);
	}

	/**
	 * Getting the Collection of orderItem by it's locationName.
	 * 
	 * @param locationName
	 *            Location name of the particular orderItem
	 * @return ResponseBody list of orderitems
	 */
	@RequestMapping(value = "/order/getByLocationName/{locationName}", method = RequestMethod.GET)
	public Collection<OrderItem> byLocationName(@PathVariable("locationName") String locationName) {

		return orderService.getOrderByLocationName(locationName);
	}

	/**
	 * Getting the collection of orderItem by it's status.
	 * 
	 * @param status
	 *            Status of the particular orderItem
	 * @return ResponseBody list of orderitems
	 */
	@RequestMapping(value = "/order/getByStatus/{status}", method = RequestMethod.GET)
	public Collection<OrderItem> byStatus(@PathVariable("status") String status) {

		return orderService.getOrderByStatus(status);
	}

	

	/**
	 * Cancel the already booked orderItem by changing it's status.
	 * 
	 * @param orderItems
	 *            OrderItem to be cancelled
	 * @return ResponseBody OrderItems
	 */
	@RequestMapping(value = "/order/cancelByOrderId", method = RequestMethod.DELETE)
	public OrderItem cancelOrderbyOrderId(@RequestBody OrderItem orderItems) {

		return orderService.updateOrderItems(orderItems);
	}

	/**
	 * After update order change the quantity and status.
	 * 
	 * @param orderItems
	 *            OrderItem to be Updated
	 * @return ResponseBody OrderItems
	 */
	@RequestMapping(value = "/order/updateOrderQuantity", method = RequestMethod.PUT)
	public OrderItem updateOrderQuantity(@RequestBody OrderItem orderItems) {

		return orderService.updateOrderQuantity(orderItems);
	}

	

}
