package com.yourapp.foodapp.model;

import java.util.List;
import java.util.Map;

public class Order {
    private String id;
    private String customerName;
    private String customerPhone;
    private String deliveryType; // "pickup" or "delivery"
    private List<Map<String, Object>> items;
    private double total;
    private String status = "RECEIVED"; 

    public Order() {
    }

    public Order(String id, String customerName, String customerPhone, String deliveryType,
            List<Map<String, Object>> items, double total) {
        this.id = id;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.deliveryType = deliveryType;
        this.items = items;
        this.total = total;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
