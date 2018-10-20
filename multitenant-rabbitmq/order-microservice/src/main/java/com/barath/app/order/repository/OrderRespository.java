package com.barath.app.order.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import com.barath.app.order.entity.OrderItem;
import com.barath.app.order.service.OrderService;

/**
 * Interface provides the CRUD operations on OrderItems by extending Repository.
 *
 *<p>
 *This interface can be implemented to provide CRUD functionalities for OrderItems
 *see {@link OrderService} for implementation
 *</p>
 */
public interface OrderRespository extends JpaRepository<OrderItem, Long> {

	OrderItem findByOrderId(Long orderId);

	Collection<OrderItem> findByProductName(String productName);
	
	Collection<OrderItem> findByLocationName(String locationName);
	
	Collection<OrderItem> findByStatus(String status);


}
