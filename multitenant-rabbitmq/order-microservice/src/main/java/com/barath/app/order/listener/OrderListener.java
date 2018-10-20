package com.barath.app.order.listener;

import java.io.IOException;

import com.barath.app.tenancy.context.TenancyContextHolder;
import com.barath.app.tenancy.provider.DefaultTenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.barath.app.order.entity.OrderItem;
import com.barath.app.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;

/**
 * Component class that confirms the order.
 *
 */
@Component
public class OrderListener {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(OrderListener.class);


	private OrderService orderService;
	


	public OrderListener(OrderService orderService) {
		this.orderService=orderService;
		Assert.notNull(orderService,"order service cannot be null");
	}

	/**
	 * This method confirms that response message.
	 * 
	 * @param message Object that contains the response message
	 * @throws IOException
	 * 
	 */
	@RabbitListener(queues = "${order.response.queue}")
	public void confirmOrder(Message message) throws IOException{


		System.out.println("THREAD NAME "+Thread.currentThread().getName());
		String tenantId= (String) message.getMessageProperties().getHeaders().get("TENANT_ID");
		System.out.println("TENANT FROM INVENTORY "+tenantId);
		DefaultTenant tenant=new DefaultTenant();
		tenant.setTenantIdentifier(tenantId);
		TenancyContextHolder.getStrategy().getContext().setTenant(tenant);
			if(LOGGER.isInfoEnabled()){
				LOGGER.info("Message fetched: {}",message);
			}			
			ObjectMapper map = new ObjectMapper();
			OrderItem order = 
					map.readValue(message.getBody(), OrderItem.class);
			if(LOGGER.isInfoEnabled()){
				LOGGER.info("order {}",order);
			}
			orderService.updateOrderItemsStatus(order);
		
	}


	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
}
