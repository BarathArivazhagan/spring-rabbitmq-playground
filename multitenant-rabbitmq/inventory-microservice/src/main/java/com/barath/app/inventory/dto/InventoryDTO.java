package com.barath.app.inventory.dto;

public class InventoryDTO {

    private String productName;

    private String locationName;

    private int quantity;


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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public InventoryDTO(String productName, String locationName, int quantity) {
        this.productName = productName;
        this.locationName = locationName;
        this.quantity = quantity;
    }

    public InventoryDTO() {
    }
}
