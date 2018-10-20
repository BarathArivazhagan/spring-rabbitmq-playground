package com.barath.app.inventory.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class InventoryPK implements Serializable {

	private static final long serialVersionUID = 2562451468803136805L;

	/**
	 * Default Constructor for Inventory ID.
	 */
	public InventoryPK() {
		super();

	}

	public InventoryPK(String productName, String locationName) {
		super();
		this.productName = productName;
		this.locationName = locationName;
	}

	
	@Column(name = "PRODUCT_NAME")
	private String productName;

	@Column(name = "LOCATION_NAME")
	private String locationName;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
