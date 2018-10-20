package com.barath.app.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barath.app.inventory.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	List<Inventory> findByInventoryPK_ProductName(String productName);

	List<Inventory> findByInventoryPK_LocationName(String locationName);

	Inventory findByInventoryPK_ProductNameAndInventoryPK_LocationName(String productName, String locationName);
}
