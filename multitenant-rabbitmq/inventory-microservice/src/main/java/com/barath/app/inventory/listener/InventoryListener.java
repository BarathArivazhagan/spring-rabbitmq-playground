package com.barath.app.inventory.listener;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import com.barath.app.inventory.dto.Order;
import com.barath.app.inventory.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InventoryListener {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(InventoryListener.class);
	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private InventoryService inventoryService;
	

	@RabbitListener(queues="${order.request.queue}")	
	public void placeOrder(Message message) throws IOException{

		String tenantId= (String) message.getMessageProperties().getHeaders().get("TENANT_ID");
		System.out.println("TENANT VALUE "+tenantId);
		MDC.put("TENANT_ID",tenantId);
		Order order = mapper.readValue(message.getBody(), Order.class);
		if(LOGGER.isInfoEnabled()){
			LOGGER.info("Order to be placed in inventory:{}",order);
		}
		inventoryService.updateInventory(order);
		if(LOGGER.isInfoEnabled()){
			LOGGER.info("message through inventory:{}",message);
		}
	}
	
	
	@RabbitListener(queues="${cancel.order.queue}")
	public void cancelOrder(Message message) throws IOException{
		
		Order order = mapper.readValue(message.getBody(), Order.class);
		inventoryService.updateInventory(order);
		if(LOGGER.isInfoEnabled()){
			LOGGER.info("Message inside ordeCancelled:{}",message);
		}
	}



	public InventoryListener() {
		super();
		
	}



	public InventoryListener(InventoryService inventoryService) {
		super();
		this.inventoryService = inventoryService;
	}



	public InventoryService getInventoryService() {
		return inventoryService;
	}



	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}



	
	
	
	
	
	
	
}

