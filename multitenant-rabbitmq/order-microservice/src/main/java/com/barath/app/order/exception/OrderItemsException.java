package com.barath.app.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.barath.app.order.entity.OrderItem;
import com.barath.app.order.error.GlobalErrorConstants;
import com.barath.app.order.error.GlobalErrorStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderItemsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public OrderItemsException() {
		super(GlobalErrorConstants.ERROR_404);
	}
	
	public OrderItemsException(OrderItem orderItem) {
		super("Product quantity should not be zero: " + orderItem.getQuantity());
	}
	public OrderItemsException(String productName,String locationName) {
		super("INVALID ORDER -> No such product or location name or Quantity may be zero");
	}
}
