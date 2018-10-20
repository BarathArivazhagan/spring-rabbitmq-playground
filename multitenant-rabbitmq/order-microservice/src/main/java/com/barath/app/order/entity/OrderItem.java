package com.barath.app.order.entity;

import java.lang.reflect.Constructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entity Class for OrderItems.
 * 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ORDER_ITEM_TABLE")
public class OrderItem {

	@Id
	@GeneratedValue
	@Column(name = "ORDER_ID")
	private Long orderId;

	@Column(name = "PRODUCT_NAME")
	private String productName;

	@Column(name = "LOCATION_NAME")
	private String locationName;

	@Column(name="QUANTITY")
	private Long quantity;

	@Column(name="ORDER_STATUS")
	private String status;



	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}


	public OrderItem() {

	}

	public OrderItem(String productName, String locationName, Long quantity, String status) {
		this.productName = productName;
		this.locationName = locationName;
		this.quantity = quantity;
		this.status = status;
	}
}
