package com.barath.app.inventory.service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import com.barath.app.inventory.dto.InventoryDTO;
import com.barath.app.inventory.entity.InventoryPK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barath.app.inventory.dto.Order;
import com.barath.app.inventory.entity.Inventory;
import com.barath.app.inventory.messaging.MessagePublisher;
import com.barath.app.inventory.repository.InventoryRepository;

@Service
public class InventoryService  {

	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);
	
	
	@Autowired
	private InventoryRepository repo;

	@Autowired
	private MessagePublisher messagePublisher;

	public InventoryService() {

	}

	public Inventory saveInventory(InventoryDTO inventoryDTO) {


		InventoryPK inventoryPK=new InventoryPK(inventoryDTO.getProductName(),inventoryDTO.getLocationName());
		Inventory inventory=new Inventory(inventoryPK,inventoryDTO.getQuantity());
		return repo.save(inventory);

	}

	
	public List<Inventory> getAllInventory() {
		return repo.findAll();
	}

	
	public Collection<Inventory> getByProductName(String productName) {
		return repo.findByInventoryPK_ProductName(productName);
	}

	
	public Collection<Inventory> getByLocationName(String locationName) {
		return repo.findByInventoryPK_LocationName(locationName);
	}

	
	public Inventory getByProductNameAndLocationName(String productName, String locationName) {
		return repo.findByInventoryPK_ProductNameAndInventoryPK_LocationName(productName, locationName);
	}

	
	public Inventory updateInventory(String productName, String locationName, int quantity) {
		Inventory inventory = repo.findByInventoryPK_ProductNameAndInventoryPK_LocationName(productName, locationName);
		int initialQuantity = inventory.getQuantity();
		inventory.setQuantity(initialQuantity + quantity);
		repo.save(inventory);
		return inventory;
	}

	
	public Integer getQuantity(String productName, String locationName) {
		Inventory inventory = repo.findByInventoryPK_ProductNameAndInventoryPK_LocationName(productName, locationName);
		if (inventory != null)
			return inventory.getQuantity();
		else
			return 0;
	}

	
	public Inventory updateInventory(Order orderInfo) {

		Inventory inventory = repo.findByInventoryPK_ProductNameAndInventoryPK_LocationName(orderInfo.getProductName(),
				orderInfo.getLocationName());
		try {
			if (inventory == null) {
				throw new IllegalStateException("Product or Location not found->" + "Product:"
						+ orderInfo.getProductName() + ", Location:" + orderInfo.getLocationName());
			}
			int initialQuantity = inventory.getQuantity();


			if (("PENDING").equalsIgnoreCase(orderInfo.getStatus())) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("STATUS IS PENDING");
				}
				if (initialQuantity - orderInfo.getQuantity() >= 0) {
					inventory.setQuantity(initialQuantity - orderInfo.getQuantity());
					repo.save(inventory);
					orderInfo.setStatus("BOOKED");
				} else {
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("Order is rejected as inventory is low, marking the status as CANCELLED Order id");
					}
					orderInfo.setStatus("CANCELLED");


				}
			}

		} catch (Exception e) {
			orderInfo.setStatus("CANCELLED");
			LOGGER.error(e.getMessage());
		} finally {
			messagePublisher.confirmOrder(orderInfo);
		}
		return  inventory;
	}

}
