package com.barath.app.order.service;

import java.util.Collection;

import com.barath.app.order.utils.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barath.app.order.entity.OrderItem;
import com.barath.app.order.exception.OrderItemCancelledErrorException;
import com.barath.app.order.exception.OrderItemsException;
import com.barath.app.order.messaging.MessagePublisher;
import com.barath.app.order.repository.OrderRespository;
import com.barath.app.order.utils.OrderStatus;

/**
 * Class that implements Orderservice.
 *
 * <p>
 * Service Class that provides the implementations for the OrderService API
 * Methods with the help of the OrderRepository object
 * </p>
 *
 */
@Service
public class OrderService  {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private MessagePublisher messagePublisher;

	@Autowired
	private OrderRespository orderRespository;


	/**
	 * Default constructor
	 */
	public OrderService() {
		
	}

	/**
	 * Fetching particular OrderItem by it's id.
	 * 
	 * @throws OrderItemsException
	 *             CustomException
	 * @return OrderItems result Entity
	 * @param orderId
	 *            order id
	 */
	
	public OrderItem getOrderItemById(long orderId) throws OrderItemsException {
		OrderItem productcount = orderRespository.findByOrderId(orderId);
		if (productcount == null) {
			LOGGER.error("No such Order: {}", orderId);
			throw new OrderItemsException();
		} else {
			return productcount;
		}
	}

	/**
	 * To save the given OrderItem Entity.
	 * 
	 * @return OrderItems result Entity
	 * @param orderItems
	 *            product instance
	 */
	
	public OrderItem saveOrderItems(OrderItem orderItems) {

		if (orderItems.getQuantity() > 0) {
				orderItems.setStatus(OrderStatus.PENDING);
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Order inside placed order method: {}", orderItems.toString());
				}
				OrderItem created = orderRespository.save(orderItems);
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("created:{}", created.toString());
				}
			System.out.println("THREAD NAME "+Thread.currentThread().getName());
				messagePublisher.placeOrder(created);
				return created;
		} else {
			throw new OrderItemsException();
		}
	}

	/**
	 * Updating particular OrderItem.
	 * 
	 * @throws OrderItemsException
	 *             CustomException
	 * @return OrderItems updated Entity
	 * @param orderItems
	 *            orderitem instance
	 */

	
	public OrderItem updateOrderItems(OrderItem orderItems) {

		Long orderId = orderItems.getOrderId();
		OrderItem orderSample = orderRespository.findByOrderId(orderId);
		String status = orderSample.getStatus();
		if ((OrderStatus.BOOKED).equalsIgnoreCase(status)) {
			orderSample.setStatus(OrderStatus.CANCELLED);
			orderRespository.save(orderSample);
			this.messagePublisher.placeOrder(orderSample);
		} else if ((OrderStatus.CANCELLED).equalsIgnoreCase(status)) {
			throw new OrderItemCancelledErrorException(status);
		} else {
			throw new OrderItemCancelledErrorException();
		}
		return orderSample;
	}

	
	
	
	public OrderItem updateOrderQuantity(OrderItem orderItems) {

		Long orderId = orderItems.getOrderId();
		OrderItem preOrder = orderRespository.findByOrderId(orderId);
		String status = preOrder.getStatus();
		if ((OrderStatus.BOOKED).equalsIgnoreCase(status)) {
			long reqQuantity = orderItems.getQuantity() - preOrder.getQuantity();
			orderRespository.save(preOrder);
			preOrder.setQuantity(reqQuantity);
			preOrder.setStatus(OrderStatus.BOOKED);
			this.messagePublisher.placeOrder(preOrder);
			return orderItems;
		} else if ((OrderStatus.PENDING).equalsIgnoreCase(status)) {
			if (orderItems.getQuantity() > 0) {
				orderItems.setStatus(OrderStatus.BOOKED);
				orderItems.setOrderId(null);
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Order inside update quantity method: {}", orderItems.toString());
				}
				return saveOrderItems(orderItems);
			} else {
				throw new OrderItemsException();
			}
		} else {
			throw new OrderItemCancelledErrorException();
		}
	}

	/**
	 * Updates the orderItems From the Listener.
	 * 
	 * @param orderItems
	 * @return OrderItems
	 */
	public void updateOrderItemsStatus(OrderItem orderItems) {

		if(orderRespository.exists(orderItems.getOrderId())){
			orderRespository.save(orderItems);
		}



	}

	/**
	 * It returns all the OrderItem entities stored in Database.
	 * 
	 * @return all the stored orderItems
	 */
	
	public Collection<OrderItem> getAllOrderItems() {
		return orderRespository.findAll();

	}

	/**
	 * It deletes the particular OrderItem entity in database. based on it's id.
	 * 
	 * @return String Status message
	 */
	
	public String deleteOrder(Long orderId) {

		orderRespository.delete(orderId);
		return "OrderItem deleted!";
	}

	/**
	 * Getting all the OrderItems by the particular product name.
	 * 
	 * @param productName
	 *            name of the product
	 * @return list of order items
	 */
	
	public Collection<OrderItem> getOrderByProductName(String productName) {

		return orderRespository.findByProductName(productName);
	}

	/**
	 * Getting all the OrderItems by the particular location name.
	 * 
	 * @param locationName
	 *            name of the location
	 * @return list of order items
	 */
	
	public Collection<OrderItem> getOrderByLocationName(String locationName) {
		return orderRespository.findByLocationName(locationName);
	}

	/**
	 * Getting all the OrderItems by the particular status.
	 * 
	 * @param status
	 *            order status
	 * @return list of order items
	 */
	
	public Collection<OrderItem> getOrderByStatus(String status) {
		return orderRespository.findByStatus(status);
	}



	

	public MessagePublisher getMessagePublisher() {
		return messagePublisher;
	}

	public void setMessagePublisher(MessagePublisher messagePublisher) {
		this.messagePublisher = messagePublisher;
	}

	public OrderRespository getOrderRespository() {
		return orderRespository;
	}

	public void setOrderRespository(OrderRespository orderRespository) {
		this.orderRespository = orderRespository;
	}

	

}
