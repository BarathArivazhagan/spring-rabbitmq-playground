package com.barath.app.inventory.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.barath.app.inventory.dto.InventoryDTO;
import org.hibernate.exception.DataException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.barath.app.inventory.entity.Inventory;
import com.barath.app.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(value="*")
public class InventoryController {

	protected InventoryService inventoryservice;

	public InventoryController(InventoryService inventoryservice) {
		this.inventoryservice = inventoryservice;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "Welcome to Inventory Microservice";

	}
	
	
	@RequestMapping(value = "/inventory", method = RequestMethod.POST)
	public Inventory insert(@RequestBody InventoryDTO inventory) {


		return  Optional.ofNullable(inventoryservice.saveInventory(inventory)).get();


	}

	@RequestMapping(value = "/inventory", method = RequestMethod.GET)
	public List<InventoryDTO> getInventories() {

		List<Inventory> inventories=inventoryservice.getAllInventory();
		inventories.forEach(System.out::println);
		if(inventories !=null && !inventories.isEmpty()) {
			List<InventoryDTO> inventoryDTOS=new ArrayList<>(inventories.size());
			inventories.stream().forEach(inventory -> {

				InventoryDTO dto = new InventoryDTO(inventory.getInventoryPK().getProductName(), inventory.getInventoryPK().getLocationName()
						, inventory.getQuantity());
				inventoryDTOS.add(dto);

			});


			return inventoryDTOS;
		}

		return new ArrayList<>();

	}

	@RequestMapping(value = "/inventory/getByProductName/{productName}", method = RequestMethod.GET)
	public Collection<Inventory> getByProductID(@PathVariable String productName) {

		Collection<Inventory> inventories = inventoryservice.getByProductName(productName);
		return inventories;

	}

	@RequestMapping(value = "/inventory/getByLocationName/{locationName}", method = RequestMethod.GET)
	public Collection<Inventory> getByLocationID(@PathVariable String locationName) {

		Collection<Inventory> inventories = inventoryservice.getByLocationName(locationName);
		return inventories;

	}

	
	@RequestMapping(value = "/inventory/getQuantity/{productName}/{locationName}", method = RequestMethod.GET)
	public Integer getQuantity(@PathVariable String productName, @PathVariable String locationName) {

		return inventoryservice.getQuantity(productName, locationName);
	}

	@RequestMapping(value = "/inventory/updateQuantity", method = RequestMethod.PUT)
	public Inventory updateQuantity(@RequestBody InventoryDTO inventoryDTO) {
		int quantity = inventoryDTO.getQuantity();
		return inventoryservice.updateInventory(inventoryDTO.getProductName(), inventoryDTO.getLocationName(), quantity);

	}

	@ExceptionHandler(Exception.class)
	public String DataExceptionErrorMessage(DataException e) {
		return "Deficient Quantity";
	}

}
