package com.barath.app.inventory.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "INVENTORY_TABLE")
public class Inventory implements Serializable {

	private static final long serialVersionUID = -2982238389365245074L;

	@EmbeddedId
	private InventoryPK inventoryPK;
	
	@Column(name = "Quantity")
	private int quantity;

	/**
	 * Default Constructor for Inventory
	 */
	public Inventory() {
		super();

	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public InventoryPK getInventoryPK() {
		return inventoryPK;
	}

	public void setInventoryPK(InventoryPK inventoryPK) {
		this.inventoryPK = inventoryPK;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public Inventory(InventoryPK inventoryPK, int quantity) {
		this.inventoryPK = inventoryPK;
		this.quantity = quantity;
	}
}
