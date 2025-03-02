package org.example.Model;

import java.math.BigDecimal;

public class Order {
    private int orderId;
    private int clientId;
    private int productId;
    private int quantity;
    private BigDecimal totalPrice;

    public Order(int clientId, int productId, int quantity, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public int getClientID() {
        return clientId;
    }
    public int getProductID() {
        return productId;
    }
}
